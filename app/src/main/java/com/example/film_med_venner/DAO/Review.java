package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IReview;

import java.util.ArrayList;

public class Review extends Rating implements IReview {
    private final String review;


    public Review(int rating, String username, int movieID, int reviewID, String review) {
        super(rating, username, movieID, reviewID);
        this.review = review;

    }

    public Review(int rating, String username, String movieIDStr, String reviewID, String review) {
        super(rating, username, movieIDStr, reviewID);
        this.review = review;
    }

    @Override
    public String getReview() {
        return review;
    }

    @Override
    public ArrayList<String> getReviews() {
        return null;
    }

}
