package com.example.film_med_venner.DAO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    @SerializedName("Search")
    List<Movie> movieList;

    public MovieList() {
        movieList = new ArrayList<Movie>();
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
