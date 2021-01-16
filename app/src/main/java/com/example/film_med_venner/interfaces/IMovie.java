package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IMovie {
    String getTitle();
    String getYear();
    String getPlot();
    String getDirector();
    String getRuntime();
    String getActors();
    String getGenre();
    String getType();
    //This is should return a picture
    String getPoster();
    String getImdbID();
    String getImdbReview();


    //int[] getReviews();
    //int getID();
}
