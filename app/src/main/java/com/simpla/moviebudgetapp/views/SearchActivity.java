package com.simpla.moviebudgetapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.simpla.moviebudgetapp.R;
import com.simpla.moviebudgetapp.adapters.SearchAdapter;
import com.simpla.moviebudgetapp.databinding.ActivitySearchBinding;
import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.presenters.SearchPresenter;
import com.simpla.moviebudgetapp.interfaces.SearchViewInterface;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchViewInterface {

    private ActivitySearchBinding binding;
    private SearchView searchView;
    private SearchPresenter searchPresenter;
    private SearchAdapter adapter;

    private List<MovieDetails> list = new ArrayList<>();
    private int pageNumber = 1, totalPageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupViews();
        setupMVP();
    }

    private void setupViews() {
        binding.searchToolbar.setLogo(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_movie_logo,getTheme()));
        binding.searchToolbar.setTitle("  Search");
        setSupportActionBar(binding.searchToolbar);
        binding.searchToolbar.setNavigationOnClickListener(view -> onBackPressed());
        binding.searchRw.setLayoutManager(new LinearLayoutManager(this));
        binding.searchRw.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(layoutManager == null || recyclerView.getAdapter() == null) return;
                if(dy>0 && layoutManager.findLastVisibleItemPosition() == recyclerView.getAdapter().getItemCount()-1){
                    if(pageNumber != totalPageNum){
                        pageNumber++;
                        searchPresenter.getMoreResults(String.valueOf(searchView.getQuery()),pageNumber);
                    }
                }
            }
        });
    }

    private void setupMVP(){
        searchPresenter = new SearchPresenter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Enter Movie name..");
        searchPresenter.getResultsBasedOnQuery(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(SearchActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayResult(List<MovieDetails> list1) {
        if(list1!=null) {
            if(pageNumber == 1){
                list = list1;
                adapter = new SearchAdapter(list, SearchActivity.this);
                binding.searchRw.setAdapter(adapter);
            }else{
                for(MovieDetails m : list1){
                    list.add(m);
                    adapter.notifyDataSetChanged();
                }
            }
        }else{
            Log.d("Search Activity","Movies response null");
        }
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }

    @Override
    public void getTotalPages(int num) {
        totalPageNum = num;
    }

    @Override
    public void resetSearch() { //Clearing the page with every search
        if(list != null && adapter != null) {
            list.clear();
            adapter.notifyDataSetChanged();
            pageNumber = 1;
            totalPageNum = 1;
        }
    }

    @Override
    public void resultTvChange(boolean isVisible) {
        if(isVisible){
            binding.searchTv2.setVisibility(View.VISIBLE);
            binding.searchTv.setVisibility(View.GONE);
            binding.searchRw.setVisibility(View.GONE);
        }else{
            binding.searchTv2.setVisibility(View.GONE);
            binding.searchTv.setVisibility(View.GONE);
            binding.searchRw.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setProgressBar(boolean isVisible) {
        if(isVisible){
            binding.searchProgress.setVisibility(View.VISIBLE);
            binding.searchTv.setVisibility(View.GONE);
            binding.searchTv2.setVisibility(View.GONE);
        }else
            binding.searchProgress.setVisibility(View.GONE);
    }

    @Override
    public void setTvVisible() { //"Search here" textview is visible
        binding.searchTv.setVisibility(View.VISIBLE);
        binding.searchTv2.setVisibility(View.GONE);
    }
}