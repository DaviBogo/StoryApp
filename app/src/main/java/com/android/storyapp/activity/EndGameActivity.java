package com.android.storyapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.storyapp.AppDatabase;
import com.android.storyapp.R;
import com.android.storyapp.model.EndGame;

import java.util.List;

public class EndGameActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    int jonasHappiness;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();
        jonasHappiness = Integer.parseInt(intent.getStringExtra("jonas_happiness"));

        this.mViewHolder.endGameTextView = findViewById(R.id.endGameTextView);
        this.mViewHolder.endGameTextView.setText(getEndGameByHappiness().getText());
    }

    private static class ViewHolder  {
        TextView endGameTextView;
    }

    private EndGame getEndGameByHappiness() {
        List<EndGame> endGames = getEndGames();
        for (EndGame endGame : endGames) {
            if (jonasHappiness < endGame.getMaxHappinness() && jonasHappiness > endGame.getMinHappinness()) {
                return endGame;
            }
        }
        return endGames.get(0);
    }


    private List<EndGame> getEndGames() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "jonas-database").allowMainThreadQueries().build();
        try {
            return db.endGameDAO().getAll();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void back(View view) {
        if (view.getId() == R.id.back) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
    }
}
