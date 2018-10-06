package com.example.noura.movieapp.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;



@Database(entities = {Movies.class}, version = 1 )

public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase INSTANCE;
    private static final String DATABASE_NAME = "Movies";

    public static MyDataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, "user-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract MovieADO MovieDao();

}
