package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IReview;

import java.util.ArrayList;

public class Movie implements IMovie {
    private String title;
    private String info;
    private ArrayList<String> actors;
    private ArrayList<IReview> reviews;
    private String[] genres;
    private String poster;
    private int rating;
    private int friendsRating;

    public Movie(String title, String info, ArrayList<String> actors, String[] genres, String poster) {
        this.title = title;
        this.info = info;
        this.actors = actors;
        this.genres = genres;
        this.poster = poster;
        reviews = new ArrayList<>();
    }

    public Movie(String title, String info, ArrayList<String> actors, String[] genres, String poster, int rating, int friendsRating) {
        new  Movie(title,info,actors,genres,poster);
        this.rating = rating;
        this.friendsRating = friendsRating;
        reviews = new ArrayList<>();
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setFriendsRating(int friendsRating) {
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

    @Override
    public ArrayList<IReview> getReviews() {
        return reviews;
    }
}
