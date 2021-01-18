package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;

public class Profile implements IProfile {

    private String ID;
    private String name;
    private String mvGPrefs;
    //TODO Når vi har arraylists skifter vi ints ud med dem, i det at vi så bare kan hente længden af listerne. Dette er midlertidigt.
    private ArrayList<String> friends;
    private ArrayList<String> movieReviewedIDs;
    private ArrayList<String> moviesOnToWatchList;
    private ArrayList<String> moviesOnWatchedList;


    public Profile(String name, String ID) {
        this.ID = ID;
        this.name = name;
        friends = new ArrayList<>();
        movieReviewedIDs = new ArrayList<>();
        moviesOnWatchedList = new ArrayList<>();
        moviesOnToWatchList = new ArrayList<>();
    }

    public Profile() {
        friends = new ArrayList<>();
        movieReviewedIDs = new ArrayList<>();
        moviesOnWatchedList = new ArrayList<>();
        moviesOnToWatchList = new ArrayList<>();
    }

    //TODO Den skal også hente profilbillede her
    /*public Profile(String name, String ID, ArrayList<String> moviesOnToWatchList, ArrayList<String> moviesOnWatchedList) {
        this(name, ID);
        this.moviesOnToWatchList = moviesOnToWatchList;
        this.moviesOnWatchedList = moviesOnWatchedList;
    }*/

    @Override
    public String getID() {
        return ID;
    }

    public void addFriend(String id) {
        friends.add(id);
    }

    public String[] getMoviesOnToWatchList() {
        String[] size = new String[moviesOnToWatchList.size()];
        return moviesOnToWatchList.toArray(size);
    }

    @Override
    public String[] getMoviesOnWatchedList() {
        String[] size = new String[moviesOnWatchedList.size()];
        return moviesOnWatchedList.toArray(size);
    }

    public String[] getReviewedMovies() {
        String[] size = new String[movieReviewedIDs.size()];
        return movieReviewedIDs.toArray(size);
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMvgPrefs() {
        return mvGPrefs;
    }

    @Override
    public void setMvgPrefs(String mvgPrefs) {
        this.mvGPrefs = mvgPrefs;
    }

    @Override
    public String[] getFriendIDs() {
        return friends.toArray(new String[friends.size()]);
    }


    public void setID(String ID) {
        this.ID = ID;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public void setMovieReviewedIDs(ArrayList<String> movieReviewedIDs) {
        this.movieReviewedIDs = movieReviewedIDs;
    }

    public void setMoviesOnToWatchList(ArrayList<String> moviesOnToWatchList) {
        this.moviesOnToWatchList = moviesOnToWatchList;
    }

    public void setMoviesOnWatchedList(ArrayList<String> moviesOnWatchedList) {
        this.moviesOnWatchedList = moviesOnWatchedList;
    }
}
