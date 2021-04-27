package com.android.storyapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface DecisionDAO {

    @Query("SELECT * FROM decision_table")
    List<Decision> getAll();

    @Insert
    void insertAll(Decision... decisions);

    @Delete
    void delete(Jonas jonas);

}
