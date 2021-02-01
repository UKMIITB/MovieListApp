package com.example.movielistassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movielistassignment.R;
import com.example.movielistassignment.helpers.Endpoints;
import com.example.movielistassignment.model.Movie;

public class DetailedActivity extends AppCompatActivity {

    ImageView imageViewPoster;
    TextView textViewTitle, textViewDate, textViewVoteAverage, textViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent getIntent = getIntent();
        Movie movie = (Movie) getIntent.getSerializableExtra("Movie");
        init();

        setData(movie);
    }

    private void setData(Movie movie) {
        setPoster(movie.getBackdrop_path());
        textViewTitle.setText(movie.getTitle());

        String text_date = "Released On: " + movie.getRelease_date();
        textViewDate.setText(text_date);

        String text_average_vote = "Average Rating: " + movie.getVote_average() + "/10";
        textViewVoteAverage.setText(text_average_vote);

        textViewDescription.setText(movie.getOverview());
    }

    private void setPoster(String backdrop_path) {
        String posterUrl = Endpoints.getImageUrl(backdrop_path);
        Glide.with(this)
                .load(posterUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageViewPoster);
    }

    private void init() {
        imageViewPoster = findViewById(R.id.image_view_poster);
        textViewTitle = findViewById(R.id.text_view_title);
        textViewDate = findViewById(R.id.text_view_date);
        textViewVoteAverage = findViewById(R.id.text_view_vote_average);
        textViewDescription = findViewById(R.id.text_view_description);
    }
}