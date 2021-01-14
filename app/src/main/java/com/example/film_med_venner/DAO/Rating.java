package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IRating;

import java.util.ArrayList;

public class Rating implements IRating {
    private int rating;
    private String username;
    private String userID;
    private String review;
    private String movieIDStr;
    private String ratingIDStr;

    public Rating(){

    }

    public Rating(int rating, String username, String movieIDstr, String review) {
        this.rating = rating;
        this.username = username;
        this.movieIDStr = movieIDstr;
        this.review = review;
    }
    public Rating(int rating, String username, String movieIDstr, String review, String userID) {
        this(rating,username,movieIDstr,review);
        this.userID = userID;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setMovieIDStr(String movieIDStr) {
        this.movieIDStr = movieIDStr;
    }

    public void setRatingIDStr(String ratingIDStr) {
        this.ratingIDStr = ratingIDStr;
    }

    public void setRatingID(String id){
        this.ratingIDStr = id;
    }
    @Override
    public int getRating() {
        return rating;
    }
    public String getUserID() {
        return userID;
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
