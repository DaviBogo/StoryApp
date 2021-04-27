package com.android.storyapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "jonas_table")
public class Jonas {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="happiness")
    private int happiness;
    private static final int initialHappiness = 0;

    public Jonas() {
        happiness = initialHappiness;
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

    public void addHappiness(int happiness) {
        this.happiness += happiness;
    }
}
