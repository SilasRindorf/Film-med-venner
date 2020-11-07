package com.example.film_med_venner.interfaces;

public interface IDatabase {
    IProfile getProfile(int id);
    IMovie[] getMovies();
}
