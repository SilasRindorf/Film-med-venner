package com.example.film_med_venner.interfaces;

public interface IProfile {
    String getID();

    String[] getMoviesOnToWatchList();

    String[] getMoviesOnWatchedList();

    String[] getReviewedMovies();

    String getName();

    void setName(String name);

    void setID(String id);

    String getMvgPrefs();

    void setMvgPrefs(String mvgPrefs);

    String[] getFriendIDs();


}
