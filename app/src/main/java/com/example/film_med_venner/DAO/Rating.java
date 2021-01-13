package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IRating;

import java.util.ArrayList;

public class Rating implements IRating {
    private int rating;
    private String username;
    private String review;
    private String movieIDStr;
    private String ratingIDStr;


    public Rating(int rating, String username, String movieIDstr, String ratingIDStr, String review) {
        this.rating = rating;
        this.username = username;
        this.ratingIDStr = ratingIDStr;
        this.movieIDStr = movieIDstr;
        this.review = review;
    }

    public void setRatingID(String id){
        this.ratingIDStr = id;
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
    public String getReview() {
        return review;
    }

    @Override
    public ArrayList<String> getRatings() {
        return null;
    }

    @Override
    public String getMovieIDStr() {
        return movieIDStr;
    }
}
