package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IReview;

import java.util.ArrayList;

public class ReviewController implements IController {
    private static ReviewController instance;
    public static ReviewController getInstance(){
        if (instance == null){
            instance = new ReviewController();
        }
        return instance;
    }

}
