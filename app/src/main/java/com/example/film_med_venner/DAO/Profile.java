package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;

public class Profile implements IProfile {

    private int ID;
    private String name;
    private ArrayList<String> mvGPrefs;
    private ArrayList<IProfile> friends;
    //TODO Når vi har arraylists skifter vi ints ud med dem, i det at vi så bare kan hente længden af listerne. Dette er midlertidigt.
    private int amountOfMoviesRated;
    private ArrayList<IMovie> moviesRated;
    private int amountOfMoviesReviewed;
    private int amountOfFriends;
    private int amountOfMoviesOnToWatchList;
    private int amountOfMoviesOnWatchedList;


    public Profile(String name, int ID){
        this.ID = ID;
        this.name = name;
        mvGPrefs = new ArrayList<>();
        friends = new ArrayList<>();
        moviesRated = new ArrayList<>();
        amountOfMoviesRated = -1;
    }
    //TODO Den skal også hente profilbillede her
    public Profile(String name, int ID, int amountOfMoviesRated, int amountOfMoviesReviewed, int amountOfFriends, int amountOfMoviesOnToWatchList, int amountOfMoviesOnWatchedList){
        this(name, ID);
        this.amountOfMoviesRated = amountOfMoviesRated;
        this.amountOfMoviesReviewed = amountOfMoviesReviewed;
        this.amountOfFriends = amountOfFriends;
        this.amountOfMoviesOnToWatchList = amountOfMoviesOnToWatchList;
        this.amountOfMoviesOnWatchedList = amountOfMoviesOnWatchedList;
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
        if (amountOfMoviesRated != -1)
            return amountOfMoviesRated;
        else return moviesRated.size();
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
        return friends;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMvGPrefs(ArrayList<String> mvGPrefs) {
        this.mvGPrefs = mvGPrefs;
    }


}
