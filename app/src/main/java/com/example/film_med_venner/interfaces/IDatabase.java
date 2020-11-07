package com.example.film_med_venner.interfaces;

public interface IDatabase {
    IProfile getProfile(int id);
    IProfile[] getProfiles();
    IMovie[] getMoviesWithGenre(String Genre);
    IMovie[] getMovies();
}
