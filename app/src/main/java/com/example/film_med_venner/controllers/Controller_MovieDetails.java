package com.example.film_med_venner.controllers;

import android.os.StrictMode;

import com.example.film_med_venner.API.OmdbWebServiceClient;
import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IDatabase;

public class Controller_MovieDetails implements IController {
    private IDatabase database;
    private Controller_Movie controller_movie;
    private Controller_Rating controller_rating;
    private OmdbWebServiceClient omdb = new OmdbWebServiceClient();

    private static Controller_MovieDetails instance;

    public static Controller_MovieDetails getInstance() {
        if (instance == null) {
            instance = new Controller_MovieDetails();
        }
        return instance;
    }

    private Controller_MovieDetails() {
        database = IDatabase.getInstance();
    }

    private void getControllers() {
        controller_movie = Controller_Movie.getInstance();
        controller_rating = Controller_Rating.getInstance();
    }

    public Movie getMovie(String id){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Movie movie = omdb.findMovieById(id);

        return movie;
    }

}
