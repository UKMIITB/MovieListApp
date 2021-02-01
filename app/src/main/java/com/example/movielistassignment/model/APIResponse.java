package com.example.movielistassignment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResponse {

    @SerializedName("results")
    private List<Movie> movieList;

    public APIResponse(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public APIResponse() {
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
