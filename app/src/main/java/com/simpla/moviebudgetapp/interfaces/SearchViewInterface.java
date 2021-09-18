package com.simpla.moviebudgetapp.interfaces;

import com.simpla.moviebudgetapp.models.MovieDetails;

import java.util.List;

public interface SearchViewInterface {
    void showToast(String str);
    void displayResult(List<MovieDetails> list1);
    void displayError(String s);
    void getTotalPages(int num);
    void resetSearch();
    void resultTvChange(boolean isVisible);
    void setProgressBar(boolean isVisible);
    void setTvVisible();
}
