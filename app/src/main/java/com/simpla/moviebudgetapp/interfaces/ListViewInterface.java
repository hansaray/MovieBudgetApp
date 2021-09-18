package com.simpla.moviebudgetapp.interfaces;

import com.simpla.moviebudgetapp.models.MovieDetails;

import java.util.List;

public interface ListViewInterface {

    void showToast(String s);
    void displayMovies(List<MovieDetails> list);
    void displayError(String s);
    void getTotalPages(int num);
}
