package com.example.film_med_venner.controllers;

import android.os.StrictMode;

import com.example.film_med_venner.API.OmdbWebServiceClient;
import com.example.film_med_venner.DAO.Movie;

import java.util.List;

public class Controller_Search {
    private static Controller_Search instance;
    private OmdbWebServiceClient omdb = new OmdbWebServiceClient();

    public static Controller_Search getInstance(){
        if (instance == null){
            instance = new Controller_Search();
        }
        return instance;
    }
    public List<Movie> getSearchItems(String search){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        List<Movie> movieList = omdb.searchMovieByTitle(search);

        return movieList;
    }
}
