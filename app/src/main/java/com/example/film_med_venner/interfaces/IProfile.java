package com.example.film_med_venner.interfaces;

public interface IProfile {
    String getID();

    String[] getMoviesOnToWatchList();

    String[] getMoviesOnWatchedList();

    String[] getReviewedMovies();

    String getName();

    void setName(String name);

    void setID(String id);

    String getmvGPrefs();

    void setmvGPrefs(String mvGPrefs);

    String[] getFriendIDs();


}
