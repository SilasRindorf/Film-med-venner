package com.example.film_med_venner.interfaces;

import com.example.film_med_venner.databases.DatabaseNonPers;

public interface IDatabase {
    IProfile getProfile(int id);
    IProfile[] getProfiles();
    IMovie[] getMoviesWithGenre(String Genre);
    IMovie[] getMovies();

    static IDatabase getInstance() {
        return DatabaseNonPers.getInstance();
    }
}
