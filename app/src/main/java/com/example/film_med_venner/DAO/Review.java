package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IReview;

public class Review extends Rating implements IReview {
    private String review;

    public Review(int rating, String username, int movieID,  int reviewID, String review){
        super(rating, username, movieID, reviewID);
        this.review = review;

    }

    @Override
    public String getReview() {
        return review;
    }


}
