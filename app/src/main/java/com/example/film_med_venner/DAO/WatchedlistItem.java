package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IWatchedlistItem;

public class WatchedlistItem implements IWatchedlistItem {
    private String username;
    private IMovie movie;
    public WatchedlistItem(String username, IMovie movie){
        this.username = username;
        this.movie = movie;
    }
    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public IMovie getMovie() {
        return null;
    }
}
