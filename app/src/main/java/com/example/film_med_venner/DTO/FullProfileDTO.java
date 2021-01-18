package com.example.film_med_venner.DTO;

import java.util.List;

public class FullProfileDTO {
    private List<ProfileDTO> friends;
    private List<ReviewDTO> reviews;
    private List<String> mvGPrefs;
    private String pictureURL;
    private String ID;
    private String name;

    public String getPictureURL() {
        return pictureURL;
    }



    public List<String> getMvGPrefs() {
        return mvGPrefs;
    }

    public void setMvGPrefs(List<String> mvGPrefs) {
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
