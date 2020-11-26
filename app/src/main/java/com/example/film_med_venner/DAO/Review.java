package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IReview;

public class Review implements IReview {
    private String review;
    private String username;
    private IMovie movie;

    public Review(String review, String username, IMovie movie){
        this.review = review;
        this.username = username;
        this.movie = movie;
    }
    
    @Override
    public String getReview() {
        return review;
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
