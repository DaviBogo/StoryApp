package com.android.storyapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "dialog_table")
public class Dialog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "jonas_text")
    private String jonasText;
    @ColumnInfo(name = "answer_1_id")
    private int answer1Id;
    @ColumnInfo(name = "answer_2_id")
    private int answer2Id;

    @Ignore
    public Answer previousAnswer;
    @Ignore
    public Answer answer1;
    @Ignore
    public Answer answer2;

    public Dialog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJonasText() {
        return jonasText;
    }

    public void setJonasText(String jonasText) {
        this.jonasText = jonasText;
    }

    public int getAnswer1Id() {
        return answer1Id;
    }

    public void setAnswer1Id(int answer1Id) {
        this.answer1Id = answer1Id;
    }

    public int getAnswer2Id() {
        return answer2Id;
    }

    public void setAnswer2Id(int answer2Id) {
        this.answer2Id = answer2Id;
    }
}
