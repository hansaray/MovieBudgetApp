package com.simpla.moviebudgetapp.roomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDao {

    @Query("Select * from favorites")
    LiveData<List<Favorites>> getFavList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(Iterable<Favorites> favorites);

    @Update
    void updateUser(Favorites favorites);

    @Delete
    void deleteUser(Favorites favorites);

}
