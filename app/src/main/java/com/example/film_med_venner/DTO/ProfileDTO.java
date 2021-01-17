package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileDTO {
    private String ID;
    private String name;
    private List<String> mvGPrefs;
    private List<String> amountOfMoviesReviewed;
    private List<String> friends;
    private List<Integer> moviesReviewedIDs;
    private List<String> amountOfMoviesOnToWatchList;
    private List<String> amountOfMoviesOnWatchedList;

    public ProfileDTO() {

    }


    public ProfileDTO(IProfile profile) {
        this.ID = profile.getID();
        this.name = profile.getName();
        this.mvGPrefs = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.moviesReviewedIDs = new ArrayList<>();
        this.amountOfMoviesReviewed = Arrays.asList(profile.getReviewedMovies());
        this.amountOfMoviesOnToWatchList = Arrays.asList(profile.getMoviesOnToWatchList());
        this.amountOfMoviesOnWatchedList = Arrays.asList(profile.getMoviesOnWatchedList());
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

    public List<String> getMvGPrefs() {
        return mvGPrefs;
    }

    public void setMvGPrefs(List<String> mvGPrefs) {
        this.mvGPrefs = mvGPrefs;
    }

    public List<String> getAmountOfMoviesReviewed() {
        return amountOfMoviesReviewed;
    }

    public void setAmountOfMoviesReviewed(List<String> amountOfMoviesReviewed) {
        this.amountOfMoviesReviewed = amountOfMoviesReviewed;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<Integer> getMoviesReviewedIDs() {
        return moviesReviewedIDs;
    }

    public void setMoviesReviewedIDs(List<Integer> moviesReviewedIDs) {
        this.moviesReviewedIDs = moviesReviewedIDs;
    }

    public List<String> getAmountOfMoviesOnToWatchList() {
        return amountOfMoviesOnToWatchList;
    }

    public void setAmountOfMoviesOnToWatchList(List<String> amountOfMoviesOnToWatchList) {
        this.amountOfMoviesOnToWatchList = amountOfMoviesOnToWatchList;
    }

    public List<String> getAmountOfMoviesOnWatchedList() {
        return amountOfMoviesOnWatchedList;
    }

    public void setAmountOfMoviesOnWatchedList(List<String> amountOfMoviesOnWatchedList) {
        this.amountOfMoviesOnWatchedList = amountOfMoviesOnWatchedList;
    }
}

