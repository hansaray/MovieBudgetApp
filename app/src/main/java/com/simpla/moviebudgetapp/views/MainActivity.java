package com.simpla.moviebudgetapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.simpla.moviebudgetapp.R;
import com.simpla.moviebudgetapp.databinding.ActivityMainBinding;
import com.simpla.moviebudgetapp.interfaces.MainViewInterface;
import com.simpla.moviebudgetapp.presenters.MainPresenter;
import com.simpla.moviebudgetapp.utils.UniversalImageLoader;

public class MainActivity extends AppCompatActivity implements MainViewInterface {

    private ActivityMainBinding binding;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initImageLoader();
        setupMVP();
        setListeners();
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getApplicationContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setupMVP() {
        mainPresenter = new MainPresenter(this,this);
    }

    private void setListeners() {
        binding.mainBtnList.setOnClickListener(view -> startActivity(new Intent
                (MainActivity.this, ListActivity.class)));
        binding.mainBtnFav.setOnClickListener(view -> startActivity(new Intent
                (MainActivity.this, FavoritesActivity.class)));
        mainPresenter.handleBackground(binding.mainLayout.getBackground());
    }

    @Override
    public void setAnimation(Animation animation) {
        binding.mainLayout.setAnimation(animation);
    }

    @Override
    public void changeAnimation(boolean isOut) {
        Animation anim;
        if(isOut)
            anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
        else
            anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
        binding.mainLayout.startAnimation(anim);
    }
}