package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IWatchlistItem;

public class WatchlistItem implements IWatchlistItem {
    private String username;
    private IMovie movie;

    public WatchlistItem(String username, IMovie movie){
        this.username = username;
        this.movie = movie;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public IMovie getMovie() {
        return movie;
    }

}
