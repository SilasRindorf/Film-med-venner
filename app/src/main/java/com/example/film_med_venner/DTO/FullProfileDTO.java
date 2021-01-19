package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IWatchItem;

import java.util.List;

public class FullProfileDTO {
    private List<ProfileDTO> friends;
    private List<ReviewDTO> reviews;
    private List<IWatchItem> toWatchList;
    private List<IWatchItem> watchedList;
    private String mvGPrefs;
    private String pictureURL;
    private String ID;
    private String name;

    public List<IWatchItem> getToWatchList() {
        return toWatchList;
    }

    public void setToWatchList(List<IWatchItem> toWatchList) {
        this.toWatchList = toWatchList;
    }

    public List<IWatchItem> getWatchedList() {
        return watchedList;
    }

    public void setWatchedList(List<IWatchItem> watchedList) {
        this.watchedList = watchedList;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public String getmvGPrefs() {
        return mvGPrefs;
    }

    public void setmvGPrefs(String mvGPrefs) {
        this.mvGPrefs = mvGPrefs;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public List<ProfileDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<ProfileDTO> friends) {
        this.friends = friends;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}
