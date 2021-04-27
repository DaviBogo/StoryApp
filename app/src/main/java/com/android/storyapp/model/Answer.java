package com.android.storyapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "answer_table")
public class Answer {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private int happiness;
    @ColumnInfo(name = "next_dialog_id")
    private int nextDialogId;
    @ColumnInfo(name = "answer_text")
    private String answerText;

    @Ignore
    public Dialog previousDialog;
    @Ignore
    public Dialog nextDialog;

    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getNextDialogId() {
        return nextDialogId;
    }

    public void setNextDialogId(int nextDialogId) {
        this.nextDialogId = nextDialogId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

}

