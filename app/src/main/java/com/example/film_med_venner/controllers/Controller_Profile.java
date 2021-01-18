package com.example.film_med_venner.controllers;


import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IWatchItem;

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
        Database database = Database.getInstance();
        System.out.println("boooo");
        return instance;
    }


    public ArrayList<IWatchItem> getToWatchlistItems(){
        // Dummy data
        IMovie movie = new Movie("Bee Movie", "info", "", "new String[3]", "poster");
        IWatchItem watchlistItem = new WatchItem("Tronald Dump", "2");

        ArrayList<IWatchItem> feedList = new ArrayList<IWatchItem>();
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);

        return feedList;
    }
    public ArrayList<IWatchItem> getWatchedListItems(){
        // Dummy data
        IMovie movie = new Movie("Great Success the Movie", "info", "new ArrayList<>()", "new String[3]", "poster");
        IWatchItem watchlistItem = new WatchItem("Hurr durr", "3");

        ArrayList<IWatchItem> feedList = new ArrayList<IWatchItem>();
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
