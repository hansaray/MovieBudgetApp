package com.simpla.moviebudgetapp.interfaces;

import com.simpla.moviebudgetapp.models.MovieDetails;

import java.util.List;

public interface FavoritesViewInterface {
    void showFavorites(List<MovieDetails> list);
}
