package com.android.storyapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.storyapp.AppDatabase;
import com.android.storyapp.R;
import com.android.storyapp.model.Answer;
import com.android.storyapp.model.Dialog;
import com.android.storyapp.model.EndGame;

import java.util.ArrayList;
import java.util.List;


public class CustomizeActivity extends AppCompatActivity {

    public ViewHolder mViewHolder = new ViewHolder();

    private Dialog dialog = new Dialog();
    private Answer previousAnswer = null;
    private List<EndGame> endGames = new ArrayList<>();

    private final int minValueNumberPicker = -20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        this.mViewHolder.jonasText = findViewById(R.id.jonasText);
        this.mViewHolder.answerText1 = findViewById(R.id.answerText1);
        this.mViewHolder.answerText2 = findViewById(R.id.answerText2);
        this.mViewHolder.create = findViewById(R.id.create);
        this.mViewHolder.save = findViewById(R.id.save);
        this.mViewHolder.customizeEndGame = findViewById(R.id.customizeEndGame);
        this.mViewHolder.happinessAnswer1 = findViewById(R.id.happinessAnswer1);
        this.mViewHolder.happinessAnswer2 = findViewById(R.id.happinessAnswer2);
        this.mViewHolder.previousAnswer = findViewById(R.id.previousAnswer);

        setHappinessPickerValues();

        loadDialogFromDatabase();
        if (dialog.getId() != 0) {
            setInterfaceDisabled(dialog, dialog.answer1, dialog.answer2);
        }
    }

    private void loadDialogFromDatabase() {
        Dialog firstDialog = getFirstDialog();
        recursionToLoadDialog(firstDialog, null);
        this.dialog = firstDialog;
    }

    private void recursionToLoadDialog(Dialog dialog, Answer answer) {
        if (answer != null) {
            dialog.previousAnswer = answer;
        }
        Answer answer1 = getAnswerById(dialog.getAnswer1Id());
        Answer answer2 = getAnswerById(dialog.getAnswer2Id());
        answer1.previousDialog = dialog;
        answer2.previousDialog = dialog;
        dialog.answer1 = answer1;
        dialog.answer2 = answer2;
        if (answer1.getNextDialogId() != 0) {
            Dialog nextDialog = getDialogById(answer1.getNextDialogId());
            answer1.nextDialog = nextDialog;
            recursionToLoadDialog(nextDialog, answer1);
        }
        if (answer2.getNextDialogId() != 0) {
            Dialog nextDialog = getDialogById(answer2.getNextDialogId());
            answer2.nextDialog = nextDialog;
            recursionToLoadDialog(nextDialog, answer2);
        }
    }

    public void removeAll(View view) {
        dialog = new Dialog();
        setInterfaceEnabled();
        if (this.mViewHolder.save.isEnabled()) {
            this.mViewHolder.save.setEnabled(false);
            this.mViewHolder.save.setVisibility(View.INVISIBLE);
        }
    }


    public static class ViewHolder {
        public EditText jonasText;
        public EditText answerText1;
        public EditText answerText2;
        public Button create;
        public Button save;
        public Button customizeEndGame;
        public NumberPicker happinessAnswer1;
        public NumberPicker happinessAnswer2;
        public TextView previousAnswer;

        public EditText endGameMaxValue;
        public EditText endGameMinValue;
        public EditText endGameText;
    }

    private void setHappinessPickerValues() {
        mViewHolder.happinessAnswer1.setMinValue(0);
        int maxValue = 20;
        mViewHolder.happinessAnswer1.setMaxValue(maxValue - minValueNumberPicker);
        mViewHolder.happinessAnswer1.setValue(mViewHolder.happinessAnswer1.getValue() - minValueNumberPicker);
        mViewHolder.happinessAnswer1.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                return Integer.toString(index + minValueNumberPicker);
            }
        });
        mViewHolder.happinessAnswer2.setMinValue(0);
        mViewHolder.happinessAnswer2.setMaxValue(maxValue - minValueNumberPicker);
        mViewHolder.happinessAnswer2.setValue(mViewHolder.happinessAnswer2.getValue() - minValueNumberPicker);
        mViewHolder.happinessAnswer2.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                return Integer.toString(index + minValueNumberPicker);
            }
        });
    }

    private int getHappinessPickerValue(NumberPicker numberPicker) {
        return numberPicker.getValue() + minValueNumberPicker;
    }

    public void addDialog(View view) {
        mViewHolder.create.setEnabled(false);
        mViewHolder.happinessAnswer1.setEnabled(false);
        mViewHolder.happinessAnswer2.setEnabled(false);

        Dialog newDialog = new Dialog();
        newDialog.previousAnswer = previousAnswer;
        newDialog.setJonasText(String.valueOf(this.mViewHolder.jonasText.getText()));

        Answer answer1 = new Answer();
        answer1.setHappiness(getHappinessPickerValue(mViewHolder.happinessAnswer1));
        answer1.setAnswerText(String.valueOf(this.mViewHolder.answerText1.getText()));
        newDialog.answer1 = answer1;


        Answer answer2 = new Answer();
        answer2.setHappiness(getHappinessPickerValue(mViewHolder.happinessAnswer2));
        answer2.setAnswerText(String.valueOf(this.mViewHolder.answerText2.getText()));
        newDialog.answer2 = answer2;

        if (previousAnswer != null)
            previousAnswer.nextDialog = newDialog;
        dialog = newDialog;
    }

    public void next1(View view) {
        if (dialog.getJonasText() != null && dialog.getJonasText().contentEquals(this.mViewHolder.jonasText.getText())) {
            previousAnswer = dialog.answer1;
            previousAnswer.previousDialog = dialog;
            if (dialog.answer1.nextDialog == null) {
                setInterfaceEnabled();
                this.mViewHolder.previousAnswer.setText(dialog.answer1.getAnswerText());
            } else {
                setInterfaceDisabled(dialog.answer1.nextDialog,
                        dialog.answer1.nextDialog.answer1,
                        dialog.answer1.nextDialog.answer2);
                dialog = dialog.answer1.nextDialog;
                this.mViewHolder.previousAnswer.setText(dialog.previousAnswer.getAnswerText());
            }
        }
        if (this.mViewHolder.save.isEnabled()) {
            this.mViewHolder.save.setEnabled(false);
            this.mViewHolder.save.setVisibility(View.INVISIBLE);
        }
    }

    public void next2(View view) {
        if (dialog.getJonasText() != null && dialog.getJonasText().contentEquals(this.mViewHolder.jonasText.getText())) {
            previousAnswer = dialog.answer2;
            previousAnswer.previousDialog = dialog;
            if (dialog.answer2.nextDialog == null) {
                setInterfaceEnabled();
                this.mViewHolder.previousAnswer.setText(dialog.answer2.getAnswerText());
            } else {
                setInterfaceDisabled(dialog.answer2.nextDialog,
                        dialog.answer2.nextDialog.answer1,
                        dialog.answer2.nextDialog.answer2);
                dialog = dialog.answer2.nextDialog;
                this.mViewHolder.previousAnswer.setText(dialog.previousAnswer.getAnswerText());
            }
        }
        if (this.mViewHolder.save.isEnabled()) {
            this.mViewHolder.save.setEnabled(false);
            this.mViewHolder.save.setVisibility(View.INVISIBLE);
        }
    }

    private void setInterfaceEnabled() {
        this.mViewHolder.jonasText.setText("", TextView.BufferType.EDITABLE);
        this.mViewHolder.answerText1.setText("", TextView.BufferType.EDITABLE);
        this.mViewHolder.answerText2.setText("", TextView.BufferType.EDITABLE);
        this.mViewHolder.create.setEnabled(true);
        this.mViewHolder.happinessAnswer1.setEnabled(true);
        this.mViewHolder.happinessAnswer2.setEnabled(true);
        this.mViewHolder.happinessAnswer1.setValue(-minValueNumberPicker);
        this.mViewHolder.happinessAnswer2.setValue(-minValueNumberPicker);
        this.mViewHolder.previousAnswer.setText("");
    }

    private void setInterfaceDisabled(Dialog dialogJonas, Answer dialogAnswer1, Answer dialogAnswer2) {
        this.mViewHolder.jonasText.setText(dialogJonas.getJonasText(), TextView.BufferType.EDITABLE);
        this.mViewHolder.answerText1.setText(dialogAnswer1.getAnswerText(), TextView.BufferType.EDITABLE);
        this.mViewHolder.answerText2.setText(dialogAnswer2.getAnswerText(), TextView.BufferType.EDITABLE);
        this.mViewHolder.create.setEnabled(false);
        this.mViewHolder.happinessAnswer1.setEnabled(true);
        this.mViewHolder.happinessAnswer2.setEnabled(true);
        this.mViewHolder.happinessAnswer1.setValue(dialogAnswer1.getHappiness() - minValueNumberPicker);
        this.mViewHolder.happinessAnswer2.setValue(dialogAnswer2.getHappiness() - minValueNumberPicker);
        this.mViewHolder.happinessAnswer1.setEnabled(false);
        this.mViewHolder.happinessAnswer2.setEnabled(false);
    }

    public void back(View view) {
        if (previousAnswer != null) {
            dialog = previousAnswer.previousDialog;
            previousAnswer = dialog.previousAnswer;
            setInterfaceDisabled(dialog, dialog.answer1, dialog.answer2);
            if (previousAnswer == null) {
                setButtonSaveVisible();
                this.mViewHolder.previousAnswer.setText("");
            } else {
                this.mViewHolder.previousAnswer.setText(dialog.previousAnswer.getAnswerText());
            }
        } else {
            if (view.getId() == R.id.back) {
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
            }
        }
    }

    private void setButtonSaveVisible() {
        this.mViewHolder.save.setVisibility(View.VISIBLE);
        this.mViewHolder.save.setEnabled(true);
    }

    public void save(View view) {
        deleteDialogAndAnswerContent();

        recursionToSaveDialog(this.dialog);

        this.mViewHolder.customizeEndGame.setEnabled(true);
    }

    private int recursionToSaveDialog(Dialog dialog) {
        int idAnswer1;
        if (dialog.answer1.nextDialog == null) {
            dialog.answer1.setNextDialogId(0);
            idAnswer1 = saveAnswer(dialog.answer1);
        } else {
            int nextDialogId = recursionToSaveDialog(dialog.answer1.nextDialog);
            dialog.answer1.setNextDialogId(nextDialogId);
            idAnswer1 = saveAnswer(dialog.answer1);
        }
        int idAnswer2;
        if (dialog.answer2.nextDialog == null) {
            dialog.answer2.setNextDialogId(0);
            idAnswer2 = saveAnswer(dialog.answer2);
        } else {
            int nextDialogId = recursionToSaveDialog(dialog.answer2.nextDialog);
            dialog.answer2.setNextDialogId(nextDialogId);
            idAnswer2 = saveAnswer(dialog.answer2);
        }
        dialog.setAnswer1Id(idAnswer1);
        dialog.setAnswer2Id(idAnswer2);
        return saveDialog(dialog);
    }

    public void backToCostumize(View view) {
        setContentView(R.layout.activity_customize);

        this.mViewHolder.jonasText = findViewById(R.id.jonasText);
        this.mViewHolder.answerText1 = findViewById(R.id.answerText1);
        this.mViewHolder.answerText2 = findViewById(R.id.answerText2);
        this.mViewHolder.create = findViewById(R.id.create);
        this.mViewHolder.save = findViewById(R.id.save);
        this.mViewHolder.customizeEndGame = findViewById(R.id.customizeEndGame);
        this.mViewHolder.happinessAnswer1 = findViewById(R.id.happinessAnswer1);
        this.mViewHolder.happinessAnswer2 = findViewById(R.id.happinessAnswer2);
        this.mViewHolder.previousAnswer = findViewById(R.id.previousAnswer);
    }

    public void customizeEndGame(View view) {
        setContentView(R.layout.activity_customize_end_game);

        this.mViewHolder.endGameMaxValue = findViewById(R.id.endGameMaxValue);
        this.mViewHolder.endGameMinValue = findViewById(R.id.endGameMinValue);
        this.mViewHolder.endGameText = findViewById(R.id.endGameText);
    }

    public void addEndGame(View view) {
        EndGame endGame = new EndGame();
        endGame.setMaxHappinness(Integer.parseInt(String.valueOf(this.mViewHolder.endGameMaxValue.getText())));
        endGame.setMinHappinness(Integer.parseInt(String.valueOf(this.mViewHolder.endGameMinValue.getText())));
        endGame.setText(String.valueOf(this.mViewHolder.endGameText.getText()));
        this.endGames.add(endGame);

        this.mViewHolder.endGameMaxValue.setText("");
        this.mViewHolder.endGameMinValue.setText("");
        this.mViewHolder.endGameText.setText("");
    }

    public void finishCustomization(View view) {
        deleteEndGameContent();
        saveEndGames();
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    private Dialog getFirstDialog() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            List<Dialog> dialogs = db.dialogDAO().getAll();
            for (Dialog dialog : dialogs) {
                if (dialogs.get(dialogs.size() - 1) == dialog) {
                    return dialog;
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public int saveDialog(Dialog dialog) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            return (int) db.dialogDAO().insert(dialog);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    public int saveAnswer(Answer answer) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            return (int) db.answerDAO().insert(answer);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    private void deleteDialogAndAnswerContent() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            db.answerDAO().deleteAll();
            db.dialogDAO().deleteAll();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveEndGames() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            EndGame[] endGames = new EndGame[this.endGames.size()];
            endGames = this.endGames.toArray(endGames);
            db.endGameDAO().insertAll(endGames);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Dialog getDialogById(int dialogId) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            return db.dialogDAO().getDialogById(dialogId);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private Answer getAnswerById(int answerId) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            return db.answerDAO().getAnswerById(answerId);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void deleteEndGameContent() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            db.endGameDAO().deleteAll();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
