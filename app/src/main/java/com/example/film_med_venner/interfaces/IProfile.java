package com.example.film_med_venner.interfaces;

public interface IProfile {

    String getEmail();

    String setEmail(String email);

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
