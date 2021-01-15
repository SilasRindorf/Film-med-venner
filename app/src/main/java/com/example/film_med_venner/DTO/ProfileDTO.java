package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;

public class ProfileDTO implements IProfile {
    private String ID;
    private String name;
    private ArrayList<String> mvGPrefs;
    private int amountOfMoviesReviewed;
    private ArrayList<String> friends;
    private ArrayList<Integer> moviesReviewedIDs;
    private int amountOfMoviesOnToWatchList;
    private int amountOfMoviesOnWatchedList;
    @Override
    public String getID() {
        return null;
    }



    @Override
    public int getAmountOfMoviesReviewed() {
        return 0;
    }

    @Override
    public int getAmountOfMoviesOnToWatchList() {
        return 0;
    }

    @Override
    public int getAmountOfMoviesOnWatchedList() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String[] getMvgPrefs() {
        return new String[0];
    }

    @Override
    public void addMvgPref(String pref) {

    }

    @Override
    public void setMvgPrefs(ArrayList<String> mvgPrefs) {

    }

    @Override
    public String[] getFriendIDs() {
        return new String[0];
    }

    @Override
    public int getAmountOfFriends() {
        return 0;
    }

    @Override
    public void addFriend(String id) {

    }
}
