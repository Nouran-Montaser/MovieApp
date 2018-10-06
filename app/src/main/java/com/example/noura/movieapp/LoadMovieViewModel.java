package com.example.noura.movieapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.noura.movieapp.Data.Movies;
import com.example.noura.movieapp.Data.MyDataBase;

class LoadMovieViewModel extends ViewModel {

    private LiveData<Movies> moviesLiveData;

    public LiveData<Movies> getMoviesLiveData() {
        return moviesLiveData;
    }

    public LoadMovieViewModel(MyDataBase mDb, int mMovieId) {
        moviesLiveData = mDb.MovieDao().loadMovieById(mMovieId);
    }
}
