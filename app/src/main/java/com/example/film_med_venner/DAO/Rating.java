package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IRating;

public class Rating implements IRating {
    private int rating;
    private String username;
    private IMovie movie;

    public Rating(int rating, String username, IMovie movie){
        this.rating = rating;
        this.username = username;
        this.movie = movie;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public IMovie getMovie() {
        return movie;
    }
}
