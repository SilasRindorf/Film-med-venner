package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;

import java.util.ArrayList;

public class RatingController implements IController {
    private static RatingController instance;
    public static RatingController getInstance(){
        if (instance == null){
            instance = new RatingController();
        }
        return instance;
    }

}
