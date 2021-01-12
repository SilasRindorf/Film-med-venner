package com.example.film_med_venner.controllers;

import android.os.StrictMode;

import com.example.film_med_venner.API.OmdbWebServiceClient;
import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IDatabase;

import java.util.List;

public class Controller_MovieDetails implements IController {
    private IDatabase database;
    private Controller_Movie controller_movie;
    private Controller_Rating controller_rating;
    private Controller_Review controller_review;
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
        controller_review = Controller_Review.getInstance();
    }

    public Movie getMovie(String search){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Movie movie = omdb.findMovieByTitle(search);

        return movie;
    }

}
