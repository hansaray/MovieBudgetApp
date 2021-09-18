package com.simpla.moviebudgetapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.simpla.moviebudgetapp.R;
import com.simpla.moviebudgetapp.adapters.SearchAdapter;
import com.simpla.moviebudgetapp.databinding.ActivityListBinding;
import com.simpla.moviebudgetapp.models.MovieDetails;
import com.simpla.moviebudgetapp.presenters.ListPresenter;
import com.simpla.moviebudgetapp.interfaces.ListViewInterface;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements ListViewInterface {

    private ActivityListBinding binding;
    private SearchAdapter adapter;
    private ListPresenter listPresenter;

    private List<MovieDetails> list = new ArrayList<>();
    private int pageNumber = 1, totalPageNum = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupMVP();
        setupViews();
        getMovieList();
    }

    private void setupMVP() {
        listPresenter = new ListPresenter(this);
    }

    private void setupViews(){
        binding.listToolbar.setLogo(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_movie_logo,getTheme()));
        binding.listToolbar.setTitle("  Popular Movies");
        setSupportActionBar(binding.listToolbar);
        binding.listRw.setLayoutManager(new LinearLayoutManager(this));
        binding.listRw.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(layoutManager == null || recyclerView.getAdapter() == null) return;
                if(dy>0 && layoutManager.findLastVisibleItemPosition() == recyclerView.getAdapter().getItemCount()-1){
                    if(pageNumber != totalPageNum){
                        pageNumber++;
                        listPresenter.getMovies(pageNumber);
                    }
                }
            }
        });
    }

    private void getMovieList() {
        listPresenter.getMovies(1);
    }


    @Override
    public void showToast(String str) {
        Toast.makeText(ListActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayMovies(List<MovieDetails> list1) {
        if(list1!=null) {
            if(pageNumber == 1){
                list = list1;
                adapter = new SearchAdapter(list, ListActivity.this);
                binding.listRw.setAdapter(adapter);
            }else{
                for(MovieDetails m : list1){
                    list.add(m);
                    adapter.notifyDataSetChanged();
                }
            }
        }else{
            Log.d("ListActivity","Movies response null");
        }
    }

    @Override
    public void displayError(String e) {
        showToast(e);
    }

    @Override
    public void getTotalPages(int num) {
        totalPageNum = num;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search){
            Intent i = new Intent(ListActivity.this, SearchActivity.class);
            startActivity(i);
        }
        if(id == R.id.favories){
            Intent i = new Intent(ListActivity.this, FavoritesActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}