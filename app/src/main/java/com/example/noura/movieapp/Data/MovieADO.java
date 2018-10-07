package com.example.noura.movieapp.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface MovieADO {

    @Query("SELECT * FROM Movies")
    LiveData<List<Movies>> getAll();

    @Insert(onConflict = REPLACE)
    void insertMovie (Movies movies);

    @Query("SELECT * FROM Movies WHERE id = :id")
    LiveData<Movies> loadMovieById (int id);

    @Delete
    void deleteMovie(Movies movies);

}
