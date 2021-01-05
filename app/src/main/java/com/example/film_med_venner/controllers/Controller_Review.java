package com.example.film_med_venner.controllers;


import com.example.film_med_venner.databases.DatabaseNonPers;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;


public class Controller_Review extends Controller_Rating {
    private static Controller_Review instance;
    private IDatabase database = DatabaseNonPers.getInstance();
    public static Controller_Review getInstance(){
        if (instance == null){
            instance = new Controller_Review();
        }
        return instance;
    }

    public IReview[] getReviewItems(){
        return database.getReviews();
    }

}
