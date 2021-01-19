package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IProfile;

public class ProfileDTO {
    private String ID;
    private String name;
    private String pictureURL;
    private String mvGPrefs;


    public ProfileDTO() {

    }


    public ProfileDTO(IProfile profile) {
        this.ID = profile.getID();
        this.name = profile.getName();
        this.mvGPrefs = profile.getmvGPrefs();
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmvGPrefs() {
        return mvGPrefs;
    }

    public void setmvGPrefs(String mvGPrefs) {
        this.mvGPrefs = mvGPrefs;
    }

}
