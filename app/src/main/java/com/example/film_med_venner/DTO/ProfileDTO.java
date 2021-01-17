package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;

public class ProfileDTO {
    private String ID;
    private String name;
    private ArrayList<String> mvGPrefs;
    private String[] amountOfMoviesReviewed;
    private ArrayList<String> friends;
    private ArrayList<Integer> moviesReviewedIDs;
    private String[] amountOfMoviesOnToWatchList;
    private String[] amountOfMoviesOnWatchedList;

    public ProfileDTO() {
    }


    public ProfileDTO(IProfile profile) {
        this.ID = profile.getID();
        this.name = profile.getName();
        this.mvGPrefs = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.moviesReviewedIDs = new ArrayList<>();
        this.amountOfMoviesReviewed = profile.getReviewedMovies();
        this.amountOfMoviesOnToWatchList = profile.getMoviesOnToWatchList();
        this.amountOfMoviesOnWatchedList = profile.getMoviesOnWatchedList();
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public ArrayList<String> getMvGPrefs() {
        return mvGPrefs;
    }

    public String[] getAmountOfMoviesReviewed() {
        return amountOfMoviesReviewed;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<Integer> getMoviesReviewedIDs() {
        return moviesReviewedIDs;
    }

    public String[] getAmountOfMoviesOnToWatchList() {
        return amountOfMoviesOnToWatchList;
    }

    public String[] getAmountOfMoviesOnWatchedList() {
        return amountOfMoviesOnWatchedList;
    }
}
