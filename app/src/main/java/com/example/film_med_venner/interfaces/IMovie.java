package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IMovie {
    String getTitle();
    String getInfo();
    ArrayList<String> getActors();
    String[] getGenres();
    //This is should return a picture
    String getPosterPos();
    //example 0-5 stars
    //Average of friends rating
    //int getFriendsRating();
    int[] getReviews();
    int getID();
}
