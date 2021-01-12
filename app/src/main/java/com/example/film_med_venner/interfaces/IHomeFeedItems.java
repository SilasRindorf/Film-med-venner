package com.example.film_med_venner.interfaces;

public interface IHomeFeedItems {
    // The name of the person that did something on the homefeed. eg. "->Carl<- added ____ to his watchlist".
    String getUsername();
    int getMovieID();

    String getMovieIDStr();
}
