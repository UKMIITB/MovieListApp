package com.example.movielistassignment.activities;

import android.content.Context;
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
import com.example.movielistassignment.viewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    MainActivityViewModel mainActivityViewModel;

    RecyclerView recyclerViewMoviesList;
    MoviesListRecyclerViewAdapter moviesListRecyclerViewAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Movie> movieArrayList = new ArrayList<>();

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        observeChanges();

        initRecyclerView();

    }

    private void observeChanges() {
        mainActivityViewModel.getListMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d("TAG", "Inside onChanged of observeChanges ib MainActivity");
                movieArrayList.clear();
                movieArrayList = (ArrayList<Movie>) movies;
                moviesListRecyclerViewAdapter.updateData((ArrayList<Movie>) movies);
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
        showToast(getApplicationContext(), movieClicked.getTitle());

    }

    private void showToast(Context context, String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}