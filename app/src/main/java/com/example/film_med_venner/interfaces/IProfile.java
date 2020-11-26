package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IProfile {
    //TODO Obs. på at det er ens egne reviews, ratings, to watch list, watched list, og friends man skal se når man klikker ind på en vilkårlig kategori.
    //TODO Når vi har arraylists skifter vi ints ud med dem, i det at vi så bare kan hente længden af listerne. Dette er midlertidigt.
    int getID();
    int getAmountOfMoviesRated();
    int getAmountOfMoviesReviewed();
    int getAmountOfFriends();
    int getAmountOfMoviesOnToWatchList();
    int getAmountOfMoviesOnWatchedList();
    String getName();
    ArrayList<String> getMvgPrefs();
    ArrayList<IProfile> getFriends();
}
