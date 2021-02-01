package com.example.movielistassignment.helpers;

public class Endpoints {

    public final static String API_KEY = "3babce3e760988dd462d4a45f1e20b5f";
    public final static String CONTENT_TYPE = "application/json;charset=utf-8";
    public final static String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzYmFiY2UzZTc2MDk4OGRkNDYyZDRhNDVmMWUyMGI1ZiIsInN1YiI6IjYwMTNiODJhZDk2YzNjMDA0MTBjNTE5NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.osDzgRXKgJZME5R39Tgy0Sml-9cwFLkFF77Jnk424m0";

    public static final String BASE_URL = "https://api.themoviedb.org/";

    public static String getImageUrl(String path) {
        String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
        return IMAGE_BASE_URL + path;
    }
}
