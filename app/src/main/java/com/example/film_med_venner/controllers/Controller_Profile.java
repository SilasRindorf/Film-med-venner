package com.example.film_med_venner.controllers;


import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchedlistItem;
import com.example.film_med_venner.DAO.WatchlistItem;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IWatchedlistItem;
import com.example.film_med_venner.interfaces.IWatchlistItem;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller_Profile implements IProfileController {
    private static Controller_Profile instance;
    private IDatabase database;

    private Controller_Profile(){
        database = IDatabase.getInstance();
    }
    public static Controller_Profile getInstance(){
        if (instance == null){
            instance = new Controller_Profile();
        }
        return instance;
    }


    public ArrayList<IWatchlistItem> getToWatchlistItems(){
        // Dummy data
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<>(), new String[3], "poster");
        IWatchlistItem watchlistItem = new WatchlistItem("Tronald Dump", 2);

        ArrayList<IWatchlistItem> feedList = new ArrayList<IWatchlistItem>();
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);

        return feedList;
    }
    public ArrayList<IWatchedlistItem> getWatchedListItems(){
        // Dummy data
        IMovie movie = new Movie("Great Success the Movie", "info", new ArrayList<>(), new String[3], "poster");
        IWatchedlistItem watchlistItem = new WatchedlistItem("Hurr durr", 3);

        ArrayList<IWatchedlistItem> feedList = new ArrayList<IWatchedlistItem>();
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);

        return feedList;
    }
    public ArrayList<IProfile> getFriendItems(){
        return new ArrayList<>(Arrays.asList(database.getProfiles())) ;
    }
}
