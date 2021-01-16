package com.example.film_med_venner.interfaces.IController;


import com.example.film_med_venner.interfaces.IMovie;

public interface IController_Movie extends IController {
    IMovie[] getMovies();
    int getMovieAvgReview(int movieID);
}
