package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.databases.DatabaseNonPers;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IRating;

import java.util.ArrayList;

public class RatingController implements IController {
    private static RatingController instance;
    private IDatabase database = DatabaseNonPers.getInstance();
    public static RatingController getInstance(){
        if (instance == null){
            instance = new RatingController();
        }
        return instance;
    }
    public IRating[] getRatingItems(){
        return database.getRating();
    }


}
