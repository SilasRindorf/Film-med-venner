package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IController.IController_Rating;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.runnable.RunnableRatingUI;

public class Controller_Rating implements IController_Rating {
    private static Controller_Rating instance;
    private Database database = Database.getInstance();
    public static Controller_Rating getInstance(){
        if (instance == null){
            instance = new Controller_Rating();
        }
        return instance;
    }
    public IRating[] getRatingItems(){
        return database.getRating();
    }

    public void getUserRating(String userID, String movieID, RunnableRatingUI runnable) throws IDatabase.DatabaseException {

         database.getRating(userID, movieID, runnable);

    }
}