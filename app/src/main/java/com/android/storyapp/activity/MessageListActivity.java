package com.android.storyapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.storyapp.AppDatabase;
import com.android.storyapp.R;
import com.android.storyapp.model.Answer;
import com.android.storyapp.model.Dialog;
import com.android.storyapp.model.Jonas;

import java.util.List;
import java.util.concurrent.Delayed;

public class MessageListActivity extends AppCompatActivity {

    public ViewHolder mViewHolder = new ViewHolder();
    private Dialog dialog;
    private Jonas jonas = new Jonas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        getFirstDialog();

        this.mViewHolder.textViewAnswer1 = findViewById(R.id.textviewAnswer1);
        this.mViewHolder.textViewAnswer2 = findViewById(R.id.textviewAnswer2);
        this.mViewHolder.checkBoxAnswer1 = findViewById(R.id.checkboxAnswer1);
        this.mViewHolder.checkBoxAnswer2 = findViewById(R.id.checkboxAnswer2);
        this.mViewHolder.chatLayout = findViewById(R.id.chatLayout);

        setAnswersFromDialog();
        this.mViewHolder.chatLayout.addView(createLinearLayoutDialog(dialog.getJonasText()));

        createJonasInstance();
    }

    public static class ViewHolder {
        public LinearLayout chatLayout;
        public CheckBox checkBoxAnswer1;
        public CheckBox checkBoxAnswer2;
        public TextView textViewAnswer1;
        public TextView textViewAnswer2;
    }

    public void checkBox1Clicked(View view) {
        this.mViewHolder.checkBoxAnswer2.setChecked(false);
    }

    public void checkBox2Clicked(View view) {
        this.mViewHolder.checkBoxAnswer1.setChecked(false);
    }

    public void send(View view) {
        if (this.mViewHolder.checkBoxAnswer1.isChecked()) {
            setChatDialogs(getAnswerById(dialog.getAnswer1Id()));
        } else if (this.mViewHolder.checkBoxAnswer2.isChecked()) {
            setChatDialogs(getAnswerById(dialog.getAnswer2Id()));
        }
        setAnswersFromDialog();
        this.mViewHolder.checkBoxAnswer1.setChecked(false);
        this.mViewHolder.checkBoxAnswer2.setChecked(false);
    }

    private void setChatDialogs(Answer answer) {
        this.mViewHolder.chatLayout.addView(createLinearLayoutAnswer(answer.getAnswerText()));
        if (answer.getNextDialogId() != 0) {
            dialog = getDialogById(answer.getNextDialogId());
            this.mViewHolder.chatLayout.addView(createLinearLayoutDialog(dialog.getJonasText()));
            jonas.addHappiness(answer.getHappiness());
            updateJonasHappiness(jonas.getHappiness());
        } else {
            final Intent intent = new Intent(this, EndGameActivity.class);
            intent.putExtra("jonas_happiness", String.valueOf(jonas.getHappiness()));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 4000);

        }
    }

    private void setAnswersFromDialog() {
        this.mViewHolder.textViewAnswer1.setText(getAnswerById(dialog.getAnswer1Id()).getAnswerText());
        this.mViewHolder.textViewAnswer2.setText(getAnswerById(dialog.getAnswer2Id()).getAnswerText());
    }

    private LinearLayout createLinearLayoutDialog(String text) {
        LinearLayout textLayout = new LinearLayout(this);
        textLayout.setOrientation(LinearLayout.VERTICAL);

        TextView userTextView = new TextView(this);
        userTextView.setText("outro");
        userTextView.setTextSize(14);
        textLayout.addView(userTextView);

        TextView messageTextView = new TextView(this);
        messageTextView.setText(text);
        messageTextView.setTextColor(Color.BLACK);
        messageTextView.setTextSize(16);
        textLayout.addView(messageTextView);

        return textLayout;
    }

    private LinearLayout createLinearLayoutAnswer(String text) {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        View view = new View(this);
        view.setLayoutParams(param);
        linearLayout.addView(view);

        LinearLayout textLayout = new LinearLayout(this);
        textLayout.setOrientation(LinearLayout.VERTICAL);

        TextView userTextView = new TextView(this);
        userTextView.setText("você");
        userTextView.setTextSize(14);
        userTextView.setGravity(Gravity.END);
        textLayout.addView(userTextView);

        TextView messageTextView = new TextView(this);
        messageTextView.setText(text);
        messageTextView.setTextColor(Color.BLACK);
        messageTextView.setTextSize(16);
        textLayout.addView(messageTextView);

        linearLayout.addView(textLayout);
        return linearLayout;
    }

    private void getFirstDialog() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            List<Dialog> dialogs = db.dialogDAO().getAll();
            for (Dialog dialog : dialogs) {
                if (dialogs.get(dialogs.size() - 1) == dialog) {
                    this.dialog = dialog;
                }
            }
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

    private void createJonasInstance() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            this.jonas.setId((int) db.jonasDAO().insert(this.jonas));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void updateJonasHappiness(int happiness) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            db.jonasDAO().updateJonasHappiness(happiness, this.jonas.getId());
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
