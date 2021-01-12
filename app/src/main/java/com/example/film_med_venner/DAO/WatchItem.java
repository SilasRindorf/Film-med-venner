package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IWatchItem;

public class WatchItem implements IWatchItem {
    private String username;
    private int movieID;

    public WatchItem(String username, int movieID){
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
