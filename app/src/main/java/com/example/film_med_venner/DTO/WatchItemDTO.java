package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IWatchItem;

public class WatchItemDTO {
    private String username;
    private String movieID;
    private String watchItemID;

    public WatchItemDTO(){

    }
    public WatchItemDTO(IWatchItem watchItem){
        this.username = username;
        this.movieID = watchItem.getMovieIDStr();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getWatchItemID() {
        return watchItemID;
    }

    public void setWatchItemID(String watchItemID) {
        this.watchItemID = watchItemID;
    }
}
