package com.example.film_med_venner.interfaces;

public interface IRating {
    //Reviewer
    IProfile getProfile();
    //Movie that got reviewed
    IMovie getMovie();
    //fx 0-10 stars
    int getRating();
}
