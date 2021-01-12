package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IRating;

import java.util.ArrayList;

public class Rating implements IRating {
    private int rating;
    private String username;
    private int movieID;
    private int ratingID;
    private String review;


    public Rating(int rating, String username, int movieID, int ratingID, String review){
        this.rating = rating;
        this.username = username;
        this.movieID = movieID;
        this.ratingID = ratingID;
        this.review = review;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public int getFriendsAverageRating() {
        return 0;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public int getMovieID() {
        return movieID;
    }

    @Override
    public String getReview() {
        return review;
    }

    @Override
    public ArrayList<String> getRatings() {
        return null;
    }
}
