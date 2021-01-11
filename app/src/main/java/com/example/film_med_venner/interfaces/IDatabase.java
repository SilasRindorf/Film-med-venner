package com.example.film_med_venner.interfaces;

import com.example.film_med_venner.databases.DatabaseNonPers;

import java.util.ArrayList;

public interface IDatabase {
    IProfile getProfile(int id);
    IMovie[] getMoviesWithGenre(String Genre);
    IMovie[] getMovies();
    IProfile[] getProfiles();
    ArrayList<IHomeFeedItems> getHomeFeed();
    IRating[] getRating();

    static IDatabase getInstance() {
        return DatabaseNonPers.getInstance();
    }

    class DatabaseException extends Exception{
        public DatabaseException(String message) { super(message); }
        public DatabaseException(String message, Throwable cause) { super(message, cause); }


    }
}
