package com.simpla.moviebudgetapp.presenters;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.roomDatabase.DatabaseClient;
import com.simpla.moviebudgetapp.roomDatabase.Favorites;
import com.simpla.moviebudgetapp.interfaces.FavoritesPresenterInterface;
import com.simpla.moviebudgetapp.interfaces.FavoritesViewInterface;

import java.util.ArrayList;
import java.util.List;

public class FavoritesPresenter implements FavoritesPresenterInterface {

    private final FavoritesViewInterface fvi;
    private final Context context;

    public FavoritesPresenter(FavoritesViewInterface fvi, Context context) {
        this.fvi = fvi;
        this.context = context;
    }

    @Override
    public void getRoomData(LifecycleOwner lifecycleOwner) {
        DatabaseClient.getInstance(context)
                .getAppDatabase().roomDao().getFavList().observe(lifecycleOwner, favorites -> {
            if(!favorites.isEmpty()){
                List<MovieDetails> list = new ArrayList<>();
                for(Favorites f : favorites){
                    list.add(f.getDetails());
                }
                fvi.showFavorites(list);
            }
        });
    }
}
