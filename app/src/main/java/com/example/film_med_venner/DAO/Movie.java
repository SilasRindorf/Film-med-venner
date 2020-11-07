package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;

import java.util.ArrayList;

public class Movie implements IMovie {
    private String title;
    private String info;
    private ArrayList<String> actors;
    private String[] genres;
    private String poster;
    private int rating;
    private int friendsRating;

    public Movie(String title, String info, ArrayList<String> actors, String[] genres, String poster, int rating, int friendsRating) {
        this.title = title;
        this.info = info;
        this.actors = actors;
        this.genres = genres;
        this.poster = poster;
        this.rating = rating;
        this.friendsRating = friendsRating;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public ArrayList<String> getActors() {
        return actors;
    }

    @Override
    public String[] getGenres() {
        return genres;
    }

    @Override
    public String getPoster() {
        return poster;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public int getFriendsRating() {
        return friendsRating;
    }
}
