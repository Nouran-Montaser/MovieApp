package com.example.noura.movieapp.Data;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;

    public AppExecutors(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static AppExecutors getsInstance()
    {
        if(sInstance == null)
        {
            synchronized (LOCK)
            {
                //this ensure that our database transactions are done in order so we don't have a race conditions.
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor());

            }
        }
        return sInstance;
    }

    public Executor getDiskIO (){return diskIO;}

}
