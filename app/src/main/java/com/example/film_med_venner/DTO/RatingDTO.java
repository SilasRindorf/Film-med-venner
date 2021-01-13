package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IRating;

import java.util.ArrayList;

public class RatingDTO {
    private int rating;
    private String username;
    private String review;
    private String movieIDStr;

    public RatingDTO(IRating rating){
        this.rating = rating.getRating();
        this.username = rating.getUsername();
        this.movieIDStr = rating.getMovieIDStr();
        this.review = rating.getReview();
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
}
