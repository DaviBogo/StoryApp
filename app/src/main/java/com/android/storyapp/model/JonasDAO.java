package com.android.storyapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JonasDAO {

    @Query("SELECT * FROM jonas_table")
    List<Jonas> getAll();

    @Query("UPDATE jonas_table SET happiness = :happiness WHERE id = :id")
    void updateJonasHappiness(int happiness, int id);

    @Insert
    void insertAll(Jonas... jonas);

    @Insert
    long insert(Jonas jonas);

    @Delete
    void delete (Jonas jonas);
}
