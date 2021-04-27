package com.android.storyapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity(tableName = "decision_table")
public class Decision {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="answer_id")
    private int answerId;
    @ColumnInfo(name="dialog_id")
    private int dialogId;

    public Decision() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getDialogId() {
        return dialogId;
    }

    public void setDialogId(int dialogId) {
        this.dialogId = dialogId;
    }
}
