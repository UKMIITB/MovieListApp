package com.example.movielistassignment.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielistassignment.ItemClickListener;
import com.example.movielistassignment.R;
import com.example.movielistassignment.adapters.MoviesListRecyclerViewAdapter;
import com.example.movielistassignment.model.Movie;
import com.example.movielistassignment.repository.room.MovieDatabase;
import com.example.movielistassignment.viewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    MainActivityViewModel mainActivityViewModel;

    RecyclerView recyclerViewMoviesList;
    MoviesListRecyclerViewAdapter moviesListRecyclerViewAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Movie> movieArrayList = new ArrayList<>();
    MovieDatabase movieDatabase;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        movieDatabase = MovieDatabase.getInstance(getApplicationContext());
        observeChanges();

        initRecyclerView();
        getMovieListLocalDB();
    }

    private void observeChanges() {
        mainActivityViewModel.getListMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d("TAG", "Inside onChanged of observeChanges ib MainActivity");
                Log.d("TAG", "Size is :" + movies.size());
                movieArrayList.clear();
                movieArrayList = (ArrayList<Movie>) movies;

                refreshLocalDB(movies);
                //moviesListRecyclerViewAdapter.updateData((ArrayList<Movie>) movies);
            }
        });
    }

    private void getMovieListLocalDB() {
        movieDatabase.movieDao().getMovieList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Movie>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("TAG", "Inside onSubscribe of getMovieListLocalDB in MainActivity");
                    }

                    @Override
                    public void onSuccess(@NonNull List<Movie> movies) {
                        movieArrayList = (ArrayList<Movie>) movies;
                        moviesListRecyclerViewAdapter.updateData((ArrayList<Movie>) movies);
                        Log.d("TAG", "onSuccess of getMovieListLocalDB");
                        Log.d("TAG", "" + movies.size());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError of getMovieListLocalDB" + e.getMessage());
                    }
                });
    }

    private void refreshLocalDB(List<Movie> movies) {
        clearLocalDB(movies);
    }

    private void clearLocalDB(List<Movie> movies) {
        movieDatabase.movieDao().clearDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("TAG", "Inside onSubscribe of updateLocalDB clearDB");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "Inside onComplete of updateLocalDB clearDB");
                        addToLocalDB(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "Inside onError of updateLocalDB clearDB");
                    }
                });
    }

    private void addToLocalDB(List<Movie> movies) {
        movieDatabase.movieDao().updateMovies(movies)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("TAG", "Inside onSubscribe of updateLocalDB updateMovies");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "Inside onComplete of updateLocalDB updateMovies");
                        getMovieListLocalDB();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "Inside onError of updateLocalDB updateMovies");
                    }
                });
    }

    private void initRecyclerView() {
        recyclerViewMoviesList = findViewById(R.id.recycler_view_movies_list);
        layoutManager = new LinearLayoutManager(this);
        moviesListRecyclerViewAdapter = new MoviesListRecyclerViewAdapter(this, movieArrayList, this);

        recyclerViewMoviesList.setLayoutManager(layoutManager);
        recyclerViewMoviesList.setAdapter(moviesListRecyclerViewAdapter);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Movie movieClicked = movieArrayList.get(position);
        //showToast(getApplicationContext(), movieClicked.getTitle());

        Intent detailedActivityIntent = new Intent(this, DetailedActivity.class);
        detailedActivityIntent.putExtra("Movie", movieClicked);
        startActivity(detailedActivityIntent);
    }

    private void showToast(Context context, String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}