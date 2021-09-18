package com.simpla.moviebudgetapp.interfaces;

import androidx.appcompat.widget.SearchView;

public interface SearchPresenterInterface {
    void getResultsBasedOnQuery(SearchView searchView);
    void getMoreResults(String query, int pageNum);
}
