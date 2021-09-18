package com.simpla.moviebudgetapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.simpla.moviebudgetapp.R;
import com.simpla.moviebudgetapp.adapters.SearchAdapter;
import com.simpla.moviebudgetapp.databinding.ActivityFavoritesBinding;
import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.presenters.FavoritesPresenter;
import com.simpla.moviebudgetapp.interfaces.FavoritesViewInterface;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements FavoritesViewInterface {

    private ActivityFavoritesBinding binding;
    private FavoritesPresenter favoritesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupMVP();
        setViews();
    }

    private void setupMVP() {
        favoritesPresenter = new FavoritesPresenter(this,this);
    }

    private void setViews() {
        binding.favToolbar.setLogo(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_movie_logo,getTheme()));
        binding.favToolbar.setTitle("  Favorites");
        setSupportActionBar(binding.favToolbar);
        binding.favToolbar.setNavigationOnClickListener(view -> onBackPressed());
        binding.favRw.setLayoutManager(new LinearLayoutManager(this));
        favoritesPresenter.getRoomData(this);
    }

    @Override
    public void showFavorites(List<MovieDetails> list) {
        if (list == null || list.size() == 0) return;
        SearchAdapter adapter = new SearchAdapter(list, FavoritesActivity.this);
        binding.favRw.setAdapter(adapter);
    }
}