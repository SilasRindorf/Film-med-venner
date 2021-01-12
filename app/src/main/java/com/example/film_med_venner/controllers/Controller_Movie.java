package com.example.film_med_venner.controllers;

import com.example.film_med_venner.interfaces.IController.IController_Movie;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;


public class Controller_Movie implements IController_Movie {
    private IDatabase database;
    private static Controller_Movie instance;
    public static Controller_Movie getInstance(){
        if (instance == null){
            instance = new Controller_Movie();
        }
        return instance;
    }

    private Controller_Movie(){
        database = IDatabase.getInstance();
    }

    public IMovie[] getMovies(){return database.getMovies();}

    public int getMovieAvgRating(int movieID){
        int avgRating = 0;
        for (int i = 0; i <   database.getMovies().length; i++) {
//            avgRating += database.getRating()[database.getMovies()[i].getID()].getRating();
        }
        if (avgRating != 0)
            return avgRating / database.getMovies().length;
        else return 0;
    }

}
