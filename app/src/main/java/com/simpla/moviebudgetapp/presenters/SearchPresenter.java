package com.simpla.moviebudgetapp.presenters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.models.SearchResponse;
import com.simpla.moviebudgetapp.models.SearchResult;
import com.simpla.moviebudgetapp.network.NetworkClient;
import com.simpla.moviebudgetapp.network.NetworkInterface;
import com.simpla.moviebudgetapp.interfaces.SearchPresenterInterface;
import com.simpla.moviebudgetapp.interfaces.SearchViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchPresenter implements SearchPresenterInterface {

    private final String TAG = "SearchPresenter";
    SearchViewInterface searchviewInterface;
    private final List<MovieDetails> list = new ArrayList<>();
    private int totalNum = 0;

    public SearchPresenter(SearchViewInterface searchViewInterface) {
        this.searchviewInterface = searchViewInterface;
    }


    @Override
    public void getResultsBasedOnQuery(SearchView searchView) {
        getObservableQuery(searchView)
                .filter(s -> !s.equals(""))
                .debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap((Function<String, ObservableSource<SearchResponse>>) s ->
                        NetworkClient.getRetrofit().create(NetworkInterface.class)
                        .getMoviesBasedOnQuery("555dd34b51d2f5b7f9fdb39e04986933",s,1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }

    @Override
    public void getMoreResults(String query, int pageNum) {
        getObservable(query,pageNum).subscribeWith(getObserver());
    }

    public Observable<SearchResponse> getObservable(String query,int pageNum){
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getMoviesBasedOnQuery("555dd34b51d2f5b7f9fdb39e04986933",query,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<String> getObservableQuery(SearchView searchView){

        final PublishSubject<String> publishSubject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchviewInterface.resetSearch();
                searchviewInterface.setProgressBar(true);
                publishSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchviewInterface.resetSearch();
                Log.d(TAG,"OnQuery : "+newText);
                if(newText.length() != 0)
                    searchviewInterface.setProgressBar(true);
                publishSubject.onNext(newText);
                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            searchviewInterface.setProgressBar(false);
            searchviewInterface.setTvVisible();
            return false;
        });

        return publishSubject;
    }

    public DisposableObserver<SearchResponse> getObserver(){
        return new DisposableObserver<SearchResponse>() {

            @Override
            public void onNext(@NonNull SearchResponse searchResponse) {
                searchviewInterface.getTotalPages(searchResponse.getTotalPages());
                totalNum = searchResponse.getResults().size();
                searchviewInterface.setProgressBar(false);
                if(searchResponse.getResults().size() == 0) {
                    searchviewInterface.resultTvChange(true); // noMovie visible, rw invisible
                    return;
                }
                for(int i = 0; i < searchResponse.getResults().size();i++){
                    searchviewInterface.resultTvChange(false); //noMovie invisible, rw visible
                    SearchResult result = searchResponse.getResults().get(i);
                    getObservable1(result.getId()).subscribeWith(getObserver1());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                searchviewInterface.displayError("Error fetching Movie Data");
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
                    searchviewInterface.displayResult(list);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error : "+e);
                e.printStackTrace();
                searchviewInterface.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }
}
