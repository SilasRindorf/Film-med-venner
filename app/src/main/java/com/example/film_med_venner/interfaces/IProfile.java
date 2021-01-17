package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IProfile {
    String getID();

    String[] getMoviesOnToWatchList();

    String[] getMoviesOnWatchedList();

    String[] getReviewedMovies();

    String getName();

    void setName(String name);

    void setID(String id);

    String[] getMvgPrefs();

    void addMvgPref(String pref);

    void setMvgPrefs(ArrayList<String> mvgPrefs);

    String[] getFriendIDs();


}
