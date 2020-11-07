package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IMovie {
    String getTitle();
    String info();
    ArrayList<String> getActors();
    String[] getGenres();
    //This is should return a picture
    String getPoster();

}
