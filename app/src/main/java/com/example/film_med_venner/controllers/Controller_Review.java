package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IController.IController_Review;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewUI;

public class Controller_Review implements IController_Review {
    private static Controller_Review instance;
    private Database database = Database.getInstance();
    public static Controller_Review getInstance(){
        if (instance == null){
            instance = new Controller_Review();
        }
        return instance;
    }
    public IReview[] getReviewItems(){
        return database.getReview();
    }

    public void getUserReview(String userID, String movieID, RunnableReviewUI runnable) throws IDatabase.DatabaseException {
         database.getReview(userID, movieID, runnable);
    }
}