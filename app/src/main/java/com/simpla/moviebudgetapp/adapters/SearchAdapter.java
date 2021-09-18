package com.simpla.moviebudgetapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.simpla.moviebudgetapp.R;
import com.simpla.moviebudgetapp.databinding.ItemMovieBinding;
import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.views.DetailsActivity;
import com.simpla.moviebudgetapp.utils.RatingColor;
import com.simpla.moviebudgetapp.utils.UniversalImageLoader;

import java.text.DecimalFormat;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchAdapterHolder> {

    List<MovieDetails> movieList;
    Context context;

    public SearchAdapter(List<MovieDetails> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext())
                , parent, false);
        return new SearchAdapter.SearchAdapterHolder(binding);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchAdapterHolder holder, int position) {
        holder.setData(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class SearchAdapterHolder extends RecyclerView.ViewHolder {

        TextView title, budget, orgTitle, rating;
        ImageView image;
        ProgressBar progressBar;
        CardView card;

        public SearchAdapterHolder(ItemMovieBinding b) {
            super(b.getRoot());
            title = b.imTitle;
            budget = b.imBudget;
            orgTitle = b.imOriginalTitle;
            image = b.imImage;
            rating = b.imRate;
            progressBar = b.imProgressBar;
            card = b.imCard;
        }

        private void setData(MovieDetails details){
            title.setText(details.getTitle());
            //budget
            String bUtil;
            if (details.getBudget() == 0) {
                bUtil = context.getResources().getString(R.string.noBudget);
            }else{
                String number = String.valueOf(details.getBudget());
                String convertedString = new DecimalFormat("#,###.##").format(Double.parseDouble(number));
                bUtil = "$"+convertedString;
            }
            budget.setText(bUtil);
            //original title
            String util = "'"+details.getOriginalTitle()+"'";
            orgTitle.setText(util);
            //vote
            rating.setText(String.valueOf(details.getVoteAverage()));
            int color = new RatingColor().getColor(details.getVoteAverage(),context);
            rating.setTextColor(color);
            //image
            UniversalImageLoader.setImage("https://image.tmdb.org/t/p/w500/"+details.getPosterPath(), image
                    , progressBar,"");

            //Click listener on the cardView to start Details activity. Intent extra= MovieDetails object
            card.setOnClickListener(view -> context.startActivity(new Intent(context, DetailsActivity.class)
                    .putExtra("detailsObject",details)));
        }


    }
}
