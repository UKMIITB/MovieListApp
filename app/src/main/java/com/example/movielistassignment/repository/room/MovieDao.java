package com.example.movielistassignment.repository.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movielistassignment.model.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Insert
    Completable updateMovies(List<Movie> movieList);

    @Query("select * from moviedb")
    Single<List<Movie>> getMovieList();

    @Query("delete from moviedb")
    Completable clearDB();
}
