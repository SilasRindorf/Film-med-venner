package com.example.film_med_venner.controllers;

import com.example.film_med_venner.databases.DatabaseNonPers;
import com.example.film_med_venner.interfaces.IController.IController_Rating;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IRating;

public class Controller_Rating implements IController_Rating {
    private static Controller_Rating instance;
    private IDatabase database = DatabaseNonPers.getInstance();
    public static Controller_Rating getInstance(){
        if (instance == null){
            instance = new Controller_Rating();
        }
        return instance;
    }
    public IRating[] getRatingItems(){
        return database.getRating();
    }


}
