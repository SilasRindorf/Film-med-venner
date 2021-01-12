package com.example.film_med_venner.controllers;

import android.os.StrictMode;

import com.example.film_med_venner.API.OmdbWebServiceClient;
import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Search;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.ISearch;

import java.util.ArrayList;
import java.util.List;

public class SearchController {
    private static SearchController instance;
    private OmdbWebServiceClient omdb = new OmdbWebServiceClient();

    public static SearchController getInstance(){
        if (instance == null){
            instance = new SearchController();
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
