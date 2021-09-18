package com.simpla.moviebudgetapp.presenters;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.simpla.moviebudgetapp.R;
import com.simpla.moviebudgetapp.interfaces.MainPresenterInterface;
import com.simpla.moviebudgetapp.interfaces.MainViewInterface;

public class MainPresenter implements MainPresenterInterface {

    private final MainViewInterface mvi;
    private final Context context;

    public MainPresenter(MainViewInterface mvi, Context context) {
        this.mvi = mvi;
        this.context = context;
    }

    @Override
    public void handleBackground(Drawable background) {
        AnimationDrawable animationDrawable = (AnimationDrawable) background;
        animationDrawable.start();
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        mvi.setAnimation(animation);
        animation.start();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mvi.changeAnimation(false);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mvi.changeAnimation(true);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
