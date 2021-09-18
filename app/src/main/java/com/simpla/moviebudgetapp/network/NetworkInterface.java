package com.simpla.moviebudgetapp.network;

import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.models.SearchResponse;

import io.reactivex.rxjava3.core.Observable;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkInterface {
    @GET("movie/popular") //main page call. Popular movies
    Observable<SearchResponse> getMovies(@Query("api_key") String api_key, @Query("page") int page);
    @GET("search/movie") //search call.
    Observable<SearchResponse> getMoviesBasedOnQuery(@Query("api_key") String api_key, @Query("query") String q, @Query("page") int page);
    @GET("movie/{movie_id}") //movie details call.
    Observable<MovieDetails> getDetails(@Path("movie_id") Integer id, @Query("api_key") String api_key);
}
