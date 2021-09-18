package com.simpla.moviebudgetapp.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simpla.moviebudgetapp.models.MovieDetails;

import java.lang.reflect.Type;

public class Converters {

    @TypeConverter
    public static MovieDetails fromString(String value) {
        Type type = new TypeToken<MovieDetails>() {}.getType();
        return new Gson().fromJson(value,type);
    }

    @TypeConverter
    public static String detailsToString(MovieDetails details) {
        Gson gson = new Gson();
        return gson.toJson(details);
    }

}
