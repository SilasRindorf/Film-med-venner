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

    public static SearchController getInstance(){
        if (instance == null){
            instance = new SearchController();
        }
        return instance;
    }
    public List<Movie> getSearchItems(String search){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        List<Movie> movieList = OmdbWebServiceClient.searchMovieByTitle(search);

//        // Dummy data
//        ISearch search1 = new Search("Trending","");
//        ISearch search2 = new Search("Popular","");
//        ISearch search3 = new Search("Shocking","");
//        ISearch search4 = new Search("Favourites","");
//
//        ArrayList<ISearch> feedList = new ArrayList<ISearch>();
//
//        feedList.add(search1);
//        feedList.add(search2);
//        feedList.add(search3);
//        feedList.add(search4);

        return movieList;
    }
}
