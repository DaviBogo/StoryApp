package com.android.storyapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.storyapp.AppDatabase;
import com.android.storyapp.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void startGame(View view) {
        if (view.getId() == R.id.playButton) {
            Intent intent = new Intent(this, MessageListActivity.class);
            startActivity(intent);
        }
    }

    public void customize(View view) {
        if (view.getId() == R.id.customizeButton) {
            Intent intent = new Intent(this, CustomizeActivity.class);
            startActivity(intent);
        }
    }
}
