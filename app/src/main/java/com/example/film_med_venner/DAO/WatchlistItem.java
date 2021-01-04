package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IWatchlistItem;

public class WatchlistItem implements IWatchlistItem {
    private String username;
    private int movieID;

    public WatchlistItem(String username, int movieID){
        this.username = username;
        this.movieID = movieID;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public int getMovieID() {
        return movieID;
    }

}
