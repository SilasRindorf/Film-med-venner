package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Search;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.ISearch;

import java.util.ArrayList;

public class SearchController {
    private static SearchController instance;

    public static SearchController getInstance(){
        if (instance == null){
            instance = new SearchController();
        }
        return instance;
    }
    public ArrayList<ISearch> getSearchItems(){
        System.out.println("SearchController before doing stuff");
        // Dummy data
        ISearch search1 = new Search("Trending","");
        ISearch search2 = new Search("Populaire","");
        ISearch search3 = new Search("Shocking","");
        ISearch search4 = new Search("Favourites","");

        ArrayList<ISearch> feedList = new ArrayList<ISearch>();

        feedList.add(search1);
        feedList.add(search2);
        feedList.add(search3);
        feedList.add(search4);
        System.out.println("SearchController after doing stuff");

        return feedList;
    }
}
