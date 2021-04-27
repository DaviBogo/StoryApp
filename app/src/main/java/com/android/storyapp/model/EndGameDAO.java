package com.android.storyapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EndGameDAO {

    @Query("SELECT * FROM end_game_table")
    List<EndGame> getAll();

    @Insert
    void insertAll(EndGame... endGames);

    @Query("DELETE FROM end_game_table")
    void deleteAll();

    @Delete
    void delete (EndGame endGame);
}
