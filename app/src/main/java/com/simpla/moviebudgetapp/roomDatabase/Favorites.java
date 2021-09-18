package com.simpla.moviebudgetapp.roomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.utils.Converters;

import java.io.Serializable;

@Entity(tableName = "favorites")
public class Favorites implements Serializable {

    @PrimaryKey
    private int movieID;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "details")
    private MovieDetails details;

    /**
     * @param movieID- MovieId
     * @param details- MovieDetails object which has all the information about the movie
     */
    public Favorites(int movieID, MovieDetails details) {
        this.movieID = movieID;
        this.details = details;
    }

    /**
     * Getter and Setter Functions
     */

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public MovieDetails getDetails() {
        return details;
    }

    public void setDetails(MovieDetails details) {
        this.details = details;
    }

}

