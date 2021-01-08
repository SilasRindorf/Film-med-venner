package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IMovie {
    String getTitle();
    String getSummary();
    ArrayList<String> getDirectors();
    double getRuntime();
    ArrayList<String> getActors();
    String[] getGenres();
    //This is should return a picture
    String getPosterPos();
    int[] getReviews();
    int getID();
}
