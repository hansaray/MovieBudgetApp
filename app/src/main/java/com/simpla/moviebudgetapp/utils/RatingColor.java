package com.simpla.moviebudgetapp.utils;

import android.content.Context;

import com.simpla.moviebudgetapp.R;

public class RatingColor {

    public int getColor(float number, Context context){
        if(number == 0)
            return context.getResources().getColor(R.color.ratingGrey);
        else if (number >= 0.1 && number <= 2.99)
            return context.getResources().getColor(R.color.ratingRed);
        else if (number >= 3.0 && number <= 4.99)
            return context.getResources().getColor(R.color.ratingOrange);
        else if (number >= 5.0 && number <= 6.99)
            return context.getResources().getColor(R.color.ratingYellow);
        else if (number >= 7.0 && number <= 8.99)
            return context.getResources().getColor(R.color.ratingDiffGreen);
        else if (number >= 8.6 && number <= 10.0)
            return context.getResources().getColor(R.color.ratingGreen);
        else
            return context.getResources().getColor(R.color.ratingGrey);
    }
}
