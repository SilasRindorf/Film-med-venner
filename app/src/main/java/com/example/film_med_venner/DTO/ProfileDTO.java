package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;

public class ProfileDTO {
    private String ID;
    private String name;
    private ArrayList<String> mvGPrefs;
    private int amountOfMoviesReviewed;
    private ArrayList<String> friends;
    private ArrayList<Integer> moviesReviewedIDs;
    private int amountOfMoviesOnToWatchList;
    private int amountOfMoviesOnWatchedList;

    public ProfileDTO() {
    }
    public ProfileDTO(IProfile profile) {
        this.ID = profile.getID();
        this.name = profile.getName();
        this.mvGPrefs = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.moviesReviewedIDs = new ArrayList<>();
        this.amountOfMoviesReviewed = profile.getAmountOfMoviesReviewed();
        this.amountOfMoviesOnToWatchList = profile.getAmountOfMoviesOnToWatchList();
        this.amountOfMoviesOnWatchedList = profile.getAmountOfMoviesOnWatchedList();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getMvGPrefs() {
        return mvGPrefs;
    }

    public int getAmountOfMoviesReviewed() {
        return amountOfMoviesReviewed;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<Integer> getMoviesReviewedIDs() {
        return moviesReviewedIDs;
    }

    public int getAmountOfMoviesOnToWatchList() {
        return amountOfMoviesOnToWatchList;
    }

    public int getAmountOfMoviesOnWatchedList() {
        return amountOfMoviesOnWatchedList;
    }
}
