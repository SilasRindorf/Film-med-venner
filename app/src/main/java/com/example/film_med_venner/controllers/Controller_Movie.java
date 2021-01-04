package com.example.film_med_venner.controllers;

import com.example.film_med_venner.Generator;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;


public class Controller_Movie implements IController {
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

    public IMovie[] getMovies(){return new Generator().generateMovies(10);}

}
