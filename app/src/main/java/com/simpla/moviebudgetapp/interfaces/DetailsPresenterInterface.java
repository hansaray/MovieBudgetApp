package com.simpla.moviebudgetapp.interfaces;

import androidx.lifecycle.LifecycleOwner;

import com.simpla.moviebudgetapp.models.MovieDetails;

public interface DetailsPresenterInterface {
    void insertRoom(MovieDetails details);
    void deleteRoom(MovieDetails details);
    void checkRoom(int id, LifecycleOwner lifecycleOwner);
}
