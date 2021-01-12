package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IRating;

public class Rating implements IRating {
    private final int rating;
    private final String username;
    private int movieID;
    private int ratingID;
    private String movieIDStr;
    private String ratingIDStr;

    public Rating(int rating, String username, int movieID, int ratingID) {
        this.rating = rating;
        this.username = username;
        this.movieID = movieID;
        this.ratingID = ratingID;

    }

    public Rating(int rating, String username, String movieIDstr, String ratingIDStr) {
        this.rating = rating;
        this.username = username;
        this.ratingIDStr = ratingIDStr;
        this.movieIDStr = movieIDstr;

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
    public String getMovieIDStr() {
        return movieIDStr;
    }
}
