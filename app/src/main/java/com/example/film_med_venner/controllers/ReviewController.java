package com.example.film_med_venner.controllers;


import com.example.film_med_venner.databases.DatabaseNonPers;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;


public class ReviewController implements IController {
    private static ReviewController instance;
    private IDatabase database = DatabaseNonPers.getInstance();
    public static ReviewController getInstance(){
        if (instance == null){
            instance = new ReviewController();
        }
        return instance;
    }

    public IReview[] getReviewItems(){
        return database.getReviews();
    }

}
