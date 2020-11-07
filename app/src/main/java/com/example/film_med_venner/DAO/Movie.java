package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;

import java.util.ArrayList;

public class Movie implements IMovie {
    private String title;
    private String info;
    private ArrayList<String> actors;
    private String[] genres;
    private String poster;

    public Movie(String title, String info, ArrayList<String> actors, String[] genres, String poster) {
        this.title = title;
        this.info = info;
        this.actors = actors;
        this.genres = genres;
        this.poster = poster;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String info() {
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
}
