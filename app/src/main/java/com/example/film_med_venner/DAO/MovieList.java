package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    @SerializedName("Search")
    List<Movie> movieList;

    public MovieList() {
        movieList = new ArrayList<>();
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
