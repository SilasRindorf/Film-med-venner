package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IMovie {
    String getTitle();
    String getPlot();
    String getDirector();
    String getRuntime();
    String getActors();
    String getGenre();
    //This is should return a picture
    String getPoster();
    String getImdbID();
    String getImdbRating();


    //int[] getReviews();
    //int getID();
}
