package com.example.noura.movieapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.noura.movieapp.Data.MyDataBase;

public class LoadMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MyDataBase mDb;
    private final int mMovieId;

    public LoadMovieViewModelFactory(MyDataBase mDb, int mMovieId) {
        this.mDb = mDb;
        this.mMovieId = mMovieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoadMovieViewModel(mDb,mMovieId);
    }
}
