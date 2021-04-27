package com.android.storyapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface DialogDAO {

    @Query("SELECT * FROM dialog_table")
    List<Dialog> getAll();

    @Query("SELECT * FROM dialog_table WHERE id = :id")
    Dialog getDialogById(int id);

    @Query("DELETE FROM dialog_table")
    void deleteAll();

    @Insert
    long insert(Dialog dialog);

    @Delete
    void delete(Dialog dialog);

}
