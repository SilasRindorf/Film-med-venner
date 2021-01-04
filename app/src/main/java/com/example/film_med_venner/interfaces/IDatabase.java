package com.example.film_med_venner.interfaces;

import com.example.film_med_venner.databases.DatabaseNonPers;

import java.util.ArrayList;

public interface IDatabase {
    IProfile getProfile(int id);
    IMovie[] getMoviesWithGenre(String Genre);
    IMovie[] getMovies();
    IProfile[] getProfiles();
    ArrayList<IHomeFeedItems> getHomeFeed();
    IReview[] getReviews();
    IRating[] getRating();

    static IDatabase getInstance() {
        return DatabaseNonPers.getInstance();
    }
}
