package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;

public class Profile implements IProfile {

    private final String ID;
    private String name;
    private ArrayList<String> mvGPrefs;
    //TODO Når vi har arraylists skifter vi ints ud med dem, i det at vi så bare kan hente længden af listerne. Dette er midlertidigt.
    private int amountOfMoviesRated;
    private final ArrayList<String> friends;
    private final ArrayList<Integer> moviesRatedIDs;
    private int amountOfMoviesOnToWatchList;
    private int amountOfMoviesOnWatchedList;


    public Profile(String name, String ID) {
        this.ID = ID;
        this.name = name;
        mvGPrefs = new ArrayList<>();
        friends = new ArrayList<>();
        moviesRatedIDs = new ArrayList<>();
        amountOfMoviesRated = -1;
    }

    //TODO Den skal også hente profilbillede her
    public Profile(String name, String ID, int amountOfMoviesRated, int amountOfMoviesReviewed, int amountOfFriends, int amountOfMoviesOnToWatchList, int amountOfMoviesOnWatchedList) {
        this(name, ID);
        this.amountOfMoviesRated = amountOfMoviesRated;
        this.amountOfMoviesOnToWatchList = amountOfMoviesOnToWatchList;
        this.amountOfMoviesOnWatchedList = amountOfMoviesOnWatchedList;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public int getAmountOfMoviesRated() {
        if (amountOfMoviesRated != -1)
            return amountOfMoviesRated;
        else return moviesRatedIDs.size();
    }

    @Override
    public void addMoveRated(int movieID) {
        moviesRatedIDs.add(movieID);
    }

    @Override
    public int getAmountOfMoviesReviewed() {
        return moviesRatedIDs.size();
    }

    @Override
    public int getAmountOfFriends() {
        return friends.size();
    }

    @Override
    public void addFriend(String id) {
        friends.add(id);
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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String[] getMvgPrefs() {
        return (String[]) mvGPrefs.toArray();
    }

    @Override
    public void addMvgPref(String pref) {
        mvGPrefs.add(pref);
    }

    @Override
    public String[] getFriendIDs() {
        return friends.toArray(new String[friends.size()]);
    }

    @Override
    public void setMvgPrefs(ArrayList<String> mvGPrefs) {
        this.mvGPrefs = mvGPrefs;
    }


}
