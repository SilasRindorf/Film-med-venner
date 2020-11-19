package com.example.film_med_venner.interfaces;

import com.example.film_med_venner.enums.Enums;

import java.util.ArrayList;

public interface IMovie {
    String getTitle();
    String getInfo();
    ArrayList<String> getActors();
    String[] getGenres();

    //This is should return a picture
    String getPoster();
    //example 0-5 stars
    int getRating();
    //Average of friends rating
    int getFriendsRating();
    ArrayList<IReview> getReviews();

}
