package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IReview;

import java.util.Date;

public class Review implements IReview {
    private int rating;
    private String username;
    private String userID;
    private String review;
    private String movieIDStr;
    private String reviewIDStr;
    private Date creationDate;
    public Review(){

    }

    public Review(int rating, String username, String movieIDstr, String review) {
        this.rating = rating;
        this.username = username;
        this.movieIDStr = movieIDstr;
        this.review = review;
    }
    public Review(int rating, String username, String movieIDstr, String review, String userID) {
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

    public void setReviewIDStr(String reviewIDStr) {
        this.reviewIDStr = reviewIDStr;
    }

    public void setReviewID(String id){
        this.reviewIDStr = id;
    }
    @Override
    public int getRating() {
        return rating;
    }
    public String getUserID() {
        return userID;
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
    public String getMovieIDStr() {
        return movieIDStr;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
