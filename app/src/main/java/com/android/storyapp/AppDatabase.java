package com.android.storyapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.storyapp.model.Answer;
import com.android.storyapp.model.AnswerDAO;
import com.android.storyapp.model.Decision;
import com.android.storyapp.model.DecisionDAO;
import com.android.storyapp.model.Dialog;
import com.android.storyapp.model.DialogDAO;
import com.android.storyapp.model.EndGame;
import com.android.storyapp.model.EndGameDAO;
import com.android.storyapp.model.Jonas;
import com.android.storyapp.model.JonasDAO;

@Database(entities = {Jonas.class, Decision.class, Dialog.class, EndGame.class, Answer.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JonasDAO jonasDAO();
    public abstract DecisionDAO decisionDAO();
    public abstract DialogDAO dialogDAO();
    public abstract EndGameDAO endGameDAO();
    public abstract AnswerDAO answerDAO();

}
