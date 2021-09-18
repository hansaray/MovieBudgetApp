package com.simpla.moviebudgetapp.roomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.simpla.moviebudgetapp.utils.Converters;

@Database(entities = {Favorites.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract RoomDao roomDao();
}
