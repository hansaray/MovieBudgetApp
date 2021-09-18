package com.simpla.moviebudgetapp.roomDatabase;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private static DatabaseClient mInstance;

    private final AppDatabase appDatabase;

    private DatabaseClient(Context mContext){
        //Creating a room database named "MoviesRoom"
        appDatabase = Room.databaseBuilder(mContext,AppDatabase.class,"MoviesRoom").build();
    }

    public static synchronized DatabaseClient getInstance(Context mContext){
        if(mInstance==null){
            mInstance = new DatabaseClient(mContext);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase(){
        return appDatabase;
    }
}
