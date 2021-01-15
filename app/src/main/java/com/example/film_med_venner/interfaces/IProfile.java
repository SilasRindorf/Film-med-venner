package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IProfile {
    //TODO Obs. på at det er ens egne reviews, ratings, to watch list, watched list, og friends man skal se når man klikker ind på en vilkårlig kategori.
    String getID();

    int getAmountOfMoviesReviewed();

    int getAmountOfMoviesOnToWatchList();

    int getAmountOfMoviesOnWatchedList();

    String getName();

    void setName(String name);

    String[] getMvgPrefs();

    void addMvgPref(String pref);

    void setMvgPrefs(ArrayList<String> mvgPrefs);

    String[] getFriendIDs();

    int getAmountOfFriends();

    void addFriend(String id);
}
