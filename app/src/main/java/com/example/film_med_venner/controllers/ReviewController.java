package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IReview;

import java.util.ArrayList;

public class ReviewController implements IController {
    private static ReviewController instance;
    public static ReviewController getInstance(){
        if (instance == null){
            instance = new ReviewController();
        }
        return instance;
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

}
