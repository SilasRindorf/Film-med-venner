package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IWatchItem;

import java.util.Date;

public class WatchItemDTO implements IWatchItem {
    private String username;
    private String movieIDStr;
    private String watchItemID;
    private String uID;
    private Date creationDate;

    public WatchItemDTO(){

    }
    public WatchItemDTO(IWatchItem watchItem){
        this.username = username;
        this.movieIDStr = watchItem.getMovieIDStr();
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
        return movieIDStr;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMovieIDStr(String movieIDStr) {
        this.movieIDStr = movieIDStr;
    }
    public void setID(String uID) {
        this.uID = uID;
    }

    public String getWatchItemID() {
        return watchItemID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate= creationDate;
    }


    public void setWatchItemID(String watchItemID) {
        this.watchItemID = watchItemID;
    }
}
