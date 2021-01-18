package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IWatchItem;

public class WatchItemDTO implements IWatchItem {
    private String username;
    private String movieID;
    private String watchItemID;
    private String uID;

    public WatchItemDTO(){

    }
    public WatchItemDTO(IWatchItem watchItem){
        this.username = username;
        this.movieID = watchItem.getMovieIDStr();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getUserID() {
        return uID;
    }

    @Override
    public String getMovieIDStr() {
        return movieID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
    public void setID(String uID) {
        this.uID = uID;
    }

    public String getWatchItemID() {
        return watchItemID;
    }

    public void setWatchItemID(String watchItemID) {
        this.watchItemID = watchItemID;
    }
}
