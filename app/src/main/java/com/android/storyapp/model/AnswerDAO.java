package com.android.storyapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface AnswerDAO {

    @Query("SELECT * FROM answer_table")
    List<Answer> getAll();

    @Query("SELECT * FROM answer_table WHERE id = :id")
    Answer getAnswerById(int id);

    @Query("DELETE FROM answer_table")
    void deleteAll();

    @Insert
    long insert(Answer answer);

    @Delete
    void delete(Answer answer);

}
