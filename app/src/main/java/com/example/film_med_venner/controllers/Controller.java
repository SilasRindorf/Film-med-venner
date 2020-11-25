package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchedlistItem;
import com.example.film_med_venner.DAO.WatchlistItem;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.databases.DatabaseNonPers;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.IWatchedlistItem;
import com.example.film_med_venner.interfaces.IWatchlistItem;

import java.util.ArrayList;

public class Controller implements IController {
    private IDatabase database;
    private static Controller instance;
    private static ReviewController reviewController;
    private static RatingController ratingController;
    public static Controller getInstance(){
        if (instance == null){
            instance = new Controller();
        }
        return instance;
    }

    private Controller(){
        database = IDatabase.getInstance();
    }

    public IMovie[] getMovies(){return null;}
    public ArrayList<IHomeFeedItems> getHomeFeedItems(){
        // Dummy data
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<String>(), new String[3], "poster");
        IRating rating = new Rating(1000, "Kurger Bing", movie);
        IReview review = new Review("Very bee, much buzz", "Kurger Bing", movie);
        IWatchlistItem watchlistItem = new WatchlistItem("Tronald Dump", movie);

        ArrayList<IHomeFeedItems> feedList = new ArrayList<IHomeFeedItems>();
        feedList.add(rating);
        feedList.add(review);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);

        return feedList;
    }
    public ArrayList<IReview> getReviewItems(){
        // Dummy data
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<String>(), new String[3], "poster");
        IReview review = new Review("Very bee, much buzz", "Kurger Bing", movie);

        ArrayList<IReview> feedList = new ArrayList<IReview>();
        feedList.add(review);
        feedList.add(review);
        feedList.add(review);
        feedList.add(review);

        return feedList;
    }
    public ArrayList<IRating> getRatingItems(){
        // Dummy data
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<String>(), new String[3], "poster");
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
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<String>(), new String[3], "poster");
        IWatchlistItem watchlistItem = new WatchlistItem("Tronald Dump", movie);

        ArrayList<IWatchlistItem> feedList = new ArrayList<IWatchlistItem>();
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);

        return feedList;
    }
    public ArrayList<IWatchedlistItem> getWatchedlistItems(){
        // Dummy data
        IMovie movie = new Movie("Great Success the Movie", "info", new ArrayList<String>(), new String[3], "poster");
        IWatchedlistItem watchlistItem = new WatchedlistItem("Hurr durr", movie);

        ArrayList<IWatchedlistItem> feedList = new ArrayList<IWatchedlistItem>();
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);

        return feedList;
    }
}
