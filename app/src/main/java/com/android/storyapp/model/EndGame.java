package com.android.storyapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "end_game_table")
public class EndGame {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "text")
    private String text;
    @ColumnInfo(name = "max_happiness")
    private int maxHappinness;
    @ColumnInfo(name = "min_happiness")
    private int minHappinness;

    public EndGame() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMaxHappinness() {
        return maxHappinness;
    }

    public void setMaxHappinness(int maxHappinness) {
        this.maxHappinness = maxHappinness;
    }

    public int getMinHappinness() {
        return minHappinness;
    }

    public void setMinHappinness(int minHappinness) {
        this.minHappinness = minHappinness;
    }
}
