package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;

public class Profile implements IProfile {

    private int ID;
    private String name;
    private ArrayList<String> mvGPrefs;
    //TODO Når vi har arraylists skifter vi ints ud med dem, i det at vi så bare kan hente længden af listerne. Dette er midlertidigt.
    private int amountOfMoviesRated;
    private int amountOfMoviesReviewed;
    private int amountOfFriends;
    private int amountOfMoviesOnToWatchList;
    private int amountOfMoviesOnWatchedList;

    //TODO Den skal også hente profilbillede her
    public Profile(String name, int ID, int amountOfMoviesRated, int amountOfMoviesReviewed, int amountOfFriends, int amountOfMoviesOnToWatchList, int amountOfMoviesOnWatchedList){
        this.ID = ID;
        this.name = name;
        this.amountOfMoviesRated = amountOfMoviesRated;
        this.amountOfMoviesReviewed = amountOfMoviesReviewed;
        this.amountOfFriends = amountOfFriends;
        this.amountOfMoviesOnToWatchList = amountOfMoviesOnToWatchList;
        this.amountOfMoviesOnWatchedList = amountOfMoviesOnWatchedList;
        mvGPrefs = new ArrayList<>();
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getAmountOfMoviesRated() {
        return amountOfMoviesRated;
    }

    @Override
    public int getAmountOfMoviesReviewed() {
        return amountOfMoviesReviewed;
    }

    @Override
    public int getAmountOfFriends() {
        return amountOfFriends;
    }

    @Override
    public int getAmountOfMoviesOnToWatchList() {
        return amountOfMoviesOnToWatchList;
    }

    @Override
    public int getAmountOfMoviesOnWatchedList() {
        return amountOfMoviesOnWatchedList;
    }

    public String getName() {
        return name;
    }

    @Override
    public ArrayList<String> getMvgPrefs() {
        return mvGPrefs;
    }

    @Override
    public ArrayList<IProfile> getFriends() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMvGPrefs(ArrayList<String> mvGPrefs) {
        this.mvGPrefs = mvGPrefs;
    }


}
