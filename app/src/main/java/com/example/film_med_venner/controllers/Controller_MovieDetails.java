package com.example.film_med_venner.controllers;

import android.os.StrictMode;

import com.example.film_med_venner.API.OmdbWebServiceClient;
import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.interfaces.IController.IController;

public class Controller_MovieDetails implements IController {
    private static Controller_MovieDetails instance;
    private final OmdbWebServiceClient omdb = new OmdbWebServiceClient();

    public static Controller_MovieDetails getInstance() {
        if (instance == null) {
            instance = new Controller_MovieDetails();
        }
        return instance;
    }

    public Movie getMovie(String id) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Movie movie = omdb.findMovieById(id);

        return movie;
    }

}
