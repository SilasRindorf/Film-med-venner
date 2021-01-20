package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IReview;

import java.util.Date;


public class ReviewDTO implements IReview {
    private int rating;
    private String username;
    private String review;
    private String movieIDStr;
    private String userID;
    private Date creationDate;

    public ReviewDTO() {

    }

    public ReviewDTO(IReview rating, Date timestamp) {
        this.rating = rating.getRating();
        this.username = rating.getUsername();
        this.movieIDStr = rating.getMovieIDStr();
        this.review = rating.getReview();
        this.userID = rating.getUserID();
        this.creationDate = timestamp;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public void setReviewID(String id) {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getMovieIDStr() {
        return movieIDStr;
    }

    public void setMovieIDStr(String movieIDStr) {
        this.movieIDStr = movieIDStr;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
