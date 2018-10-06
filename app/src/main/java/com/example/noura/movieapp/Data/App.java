package com.example.noura.movieapp.Data;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;


public class App extends Application {

    private static final String DATABASE_NAME = "MyDatabase";
    private static final String PREFERENCES = "RoomDemo.preferences";
    private static final String KEY_FORCE_UPDATE = "force_update";
    public static App INSTANCE;
    private MyDataBase database;

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // create database
        database = Room.databaseBuilder(getApplicationContext(), MyDataBase.class, DATABASE_NAME)
                .build();

        INSTANCE = this;
    }

    public MyDataBase getDB() {
        return database;
    }

    private SharedPreferences getSP() {
        return getSharedPreferences(PREFERENCES, MODE_PRIVATE);
    }
}