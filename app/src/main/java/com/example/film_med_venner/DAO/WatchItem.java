package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IWatchItem;

public class WatchItem implements IWatchItem {
    private String username;
    private String movieID;

    public WatchItem(String username, String movieID){
        this.username = username;
        this.movieID = movieID;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public String getUserID() {
        return null;
    }

    @Override
    public String getMovieIDStr() {
        return movieID;
    }

}
