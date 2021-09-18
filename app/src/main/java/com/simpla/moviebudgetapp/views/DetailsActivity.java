package com.simpla.moviebudgetapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;

import com.simpla.moviebudgetapp.R;
import com.simpla.moviebudgetapp.databinding.ActivityDetailsBinding;
import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.presenters.DetailsPresenter;
import com.simpla.moviebudgetapp.interfaces.DetailsViewInterface;
import com.simpla.moviebudgetapp.utils.RatingColor;
import com.simpla.moviebudgetapp.utils.UniversalImageLoader;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity implements DetailsViewInterface {

    private ActivityDetailsBinding binding;
    private MovieDetails movie;
    private DetailsPresenter detailsPresenter;
    private boolean isExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentData();
        setupMVP();
        setViews();
        setData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        movie = (MovieDetails) intent.getSerializableExtra("detailsObject");
    }

    private void setupMVP() {
        detailsPresenter = new DetailsPresenter(this,this);
    }

    private void setViews() {
        binding.detailsToolbar.setLogo(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_movie_logo,getTheme()));
        binding.detailsToolbar.setTitle("  Movie Details");
        setSupportActionBar(binding.detailsToolbar);
        binding.detailsToolbar.setNavigationOnClickListener(view -> onBackPressed());
        binding.detailsBtn.setOnClickListener(view -> {
            if(movie != null) {
                if(isExist)
                    detailsPresenter.deleteRoom(movie);
                else
                    detailsPresenter.insertRoom(movie);
            }
        });
        detailsPresenter.checkRoom(movie.getId(),this);
    }

    private void setData() {
        binding.detailsTitle.setText(movie.getTitle());
        //budget
        String bUtil;
        if (movie.getBudget() == 0) {
            bUtil = getResources().getString(R.string.noBudget);
        }else{
            String number = String.valueOf(movie.getBudget());
            String convertedString = new DecimalFormat("#,###.##").format(Double.parseDouble(number));
            bUtil = "$"+convertedString;
        }
        binding.detailsBudget.setText(bUtil);
        //original title
        String util = "'"+movie.getOriginalTitle()+"'";
        binding.detailsOriginalTitle.setText(util);
        //vote
        binding.detailsRate.setText(String.valueOf(movie.getVoteAverage()));
        int color = new RatingColor().getColor(movie.getVoteAverage(),getApplicationContext());
        binding.detailsRate.setTextColor(color);
        //image
        UniversalImageLoader.setImage("https://image.tmdb.org/t/p/w500/"+movie.getPosterPath(), binding.detailsImage
                , binding.detailsProgressBar,"");
        binding.detailsDate.setText(movie.getReleaseDate());
        binding.detailsLang.setText(movie.getOriginalLanguage());
        binding.detailsOverview.setText(movie.getOverview());
    }

    @Override
    public void changeButton(boolean exist) {
        isExist = exist;
        if(exist)
            binding.detailsBtn.setText(getResources().getString(R.string.removeFromFav));
        else
            binding.detailsBtn.setText(getResources().getString(R.string.addToFav));
    }
}