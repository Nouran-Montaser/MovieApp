package com.example.noura.movieapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.noura.movieapp.Data.Movies;
import com.example.noura.movieapp.Data.MyDataBase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movies>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        MyDataBase myDataBase = MyDataBase.getAppDatabase(this.getApplication());
        movies = myDataBase.MovieDao().getAll();
    }


    public LiveData<List<Movies>> getMovies() {
        return movies;
    }

    public void setMovies(LiveData<List<Movies>> movies) {
        this.movies = movies;
    }

}
