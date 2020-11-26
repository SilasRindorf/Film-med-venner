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
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.IWatchedlistItem;
import com.example.film_med_venner.interfaces.IWatchlistItem;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileController implements IProfileController {
    private static ProfileController instance;
    private IDatabase database;

    private ProfileController(){
        database = IDatabase.getInstance();
    }
    public static ProfileController getInstance(){
        if (instance == null){
            instance = new ProfileController();
        }
        return instance;
    }

    public ArrayList<IReview> getReviewItems(){
        // Dummy data
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<>(), new String[3], "poster");
        IReview review = new Review("Very bee, much buzz", "Kurger Bing", movie);

        ArrayList<IReview> feedList = new ArrayList<>();
        feedList.add(review);
        feedList.add(review);
        feedList.add(review);
        feedList.add(review);

        return feedList;
    }
    public ArrayList<IRating> getRatingItems(){
        // Dummy data
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<>(), new String[3], "poster");
        IRating rating = new Rating(4, "Kurger Bing", movie);

        ArrayList<IRating> feedList = new ArrayList<IRating>();
        feedList.add(rating);
        feedList.add(rating);
        feedList.add(rating);
        feedList.add(rating);


        return feedList;
    }
    public ArrayList<IWatchlistItem> getToWatchlistItems(){
        // Dummy data
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<>(), new String[3], "poster");
        IWatchlistItem watchlistItem = new WatchlistItem("Tronald Dump", movie);

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
        IWatchedlistItem watchlistItem = new WatchedlistItem("Hurr durr", movie);

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
