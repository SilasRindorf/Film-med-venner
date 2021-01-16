package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IReview;


public class ReviewDTO {
    private int rating;
    private String username;
    private String review;
    private String movieIDStr;
    private String userID;


    public ReviewDTO(IReview rating){
        this.rating = rating.getRating();
        this.username = rating.getUsername();
        this.movieIDStr = rating.getMovieIDStr();
        this.review = rating.getReview();
        this.userID = rating.getUserID();
    }

    public int getRating() {
        return rating;
    }

    public String getUsername() {
        return username;
    }

    public String getReview() {
        return review;
    }

    public String getMovieIDStr() {
        return movieIDStr;
    }
    public String getUserID() {
        return userID;
    }
}
