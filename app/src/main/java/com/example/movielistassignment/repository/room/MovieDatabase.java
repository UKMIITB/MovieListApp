package com.example.movielistassignment.repository.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movielistassignment.model.Movie;

@Database(entities = Movie.class, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase movieDatabase;

    public static synchronized MovieDatabase getInstance(Context context) {
        if (movieDatabase == null)
            movieDatabase = Room.databaseBuilder(context, MovieDatabase.class, "Movie_Database").build();

        return movieDatabase;
    }

    public abstract MovieDao movieDao();
}
