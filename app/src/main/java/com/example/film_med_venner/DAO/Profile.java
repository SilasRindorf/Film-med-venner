package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

public class Profile implements IProfile {

    private String ID;
    private String name;
    private String mvGPrefs;
    private List<String> friends;
    private List<String> movieReviewedIDs;
    private List<String> moviesOnToWatchList;
    private List<String> moviesOnWatchedList;


    public Profile(String name, String ID) {
        this.ID = ID;
        this.name = name;
        this.mvGPrefs = "";
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
    public String getmvGPrefs() {
        return mvGPrefs;
    }

    @Override
    public void setmvGPrefs(String mvGPrefs) {
        this.mvGPrefs = mvGPrefs;
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
