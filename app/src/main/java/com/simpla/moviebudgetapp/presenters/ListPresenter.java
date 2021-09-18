package com.simpla.moviebudgetapp.presenters;

import android.util.Log;

import androidx.annotation.NonNull;

import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.models.SearchResponse;
import com.simpla.moviebudgetapp.models.SearchResult;
import com.simpla.moviebudgetapp.network.NetworkClient;
import com.simpla.moviebudgetapp.network.NetworkInterface;
import com.simpla.moviebudgetapp.interfaces.ListPresenterInterface;
import com.simpla.moviebudgetapp.interfaces.ListViewInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ListPresenter implements ListPresenterInterface {

    ListViewInterface mvi;
    private final String TAG = "ListPresenter";
    private final List<MovieDetails> list = new ArrayList<>();
    private int totalNum = 0;

    public ListPresenter(ListViewInterface mvi) {
        this.mvi = mvi;
    }

    @Override
    public void getMovies(int pageNum) {
        getObservable(pageNum).subscribeWith(getObserver());
    }

    public Observable<SearchResponse> getObservable(int pageNum){
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getMovies("555dd34b51d2f5b7f9fdb39e04986933",pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<SearchResponse> getObserver(){
        return new DisposableObserver<SearchResponse>() {

            @Override
            public void onNext(@NonNull SearchResponse movieResponse) {
                mvi.getTotalPages(movieResponse.getTotalPages());
                totalNum = movieResponse.getResults().size();
                for(int i = 0; i < movieResponse.getResults().size();i++){
                    SearchResult result = movieResponse.getResults().get(i);
                    getObservable1(result.getId()).subscribeWith(getObserver1());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                mvi.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    public Observable<MovieDetails> getObservable1(int id){
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getDetails(id,"555dd34b51d2f5b7f9fdb39e04986933")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<MovieDetails> getObserver1(){
        return new DisposableObserver<MovieDetails>() {

            @Override
            public void onNext(@NonNull MovieDetails SearchResponse) {
                list.add(SearchResponse);
                if(list.size() == (totalNum-1))
                    mvi.displayMovies(list);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error : "+e);
                e.printStackTrace();
                mvi.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }
}
