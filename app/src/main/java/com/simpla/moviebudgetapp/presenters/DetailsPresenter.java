package com.simpla.moviebudgetapp.presenters;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.roomDatabase.AppExecutors;
import com.simpla.moviebudgetapp.roomDatabase.DatabaseClient;
import com.simpla.moviebudgetapp.roomDatabase.Favorites;
import com.simpla.moviebudgetapp.interfaces.DetailsPresenterInterface;
import com.simpla.moviebudgetapp.interfaces.DetailsViewInterface;

import java.util.ArrayList;
import java.util.List;

public class DetailsPresenter implements DetailsPresenterInterface {

    DetailsViewInterface dvi;
    private final Context context;

    public DetailsPresenter(DetailsViewInterface dvi, Context context) {
        this.dvi = dvi;
        this.context = context;
    }

    @Override
    public void insertRoom(MovieDetails details) {
        List<Favorites> fList = new ArrayList<>();
        Favorites fav = new Favorites(details.getId(),details);
        fList.add(fav);
        AppExecutors.getInstance().diskIO().execute(() -> DatabaseClient
                .getInstance(context).getAppDatabase().roomDao().insertUser(fList));
        dvi.changeButton(true);
    }

    @Override
    public void deleteRoom(MovieDetails details) {
        Favorites fav = new Favorites(details.getId(),details);
        AppExecutors.getInstance().diskIO().execute(() -> DatabaseClient
                .getInstance(context).getAppDatabase().roomDao().deleteUser(fav));
        dvi.changeButton(false);
    }

    @Override
    public void checkRoom(int id, LifecycleOwner lifecycleOwner) {
        DatabaseClient.getInstance(context)
                .getAppDatabase().roomDao().getFavList().observe(lifecycleOwner, favorites -> {
            if(!favorites.isEmpty()){
                boolean exist = false;
                for(Favorites f : favorites){
                    if(f.getMovieID() == id) {
                        dvi.changeButton(true);
                        exist = true;
                        break;
                    }
                }
                if(!exist){
                    dvi.changeButton(false);
                }
            }
        });
    }
}
