package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;

public class Movie implements IMovie {
    private final String Title;
    private final String Year;
    private String Runtime;
    private final String Genre;
    private String Type;
    private final String Director;
    private final String Actors;
    private String Plot;
    private String imdbID;
    private String Poster;
    private String imdbReview;

    public Movie(String title, String year, String runtime, String genre, String type, String director, String actors, String plot, String imdbID, String poster, String imdbReview) {
        Title = title;
        Year = year;
        Runtime = runtime;
        Genre = genre;
        Type = type;
        Director = director;
        Actors = actors;
        Plot = plot;
        this.imdbID = imdbID;
        Poster = poster;
        this.imdbReview = imdbReview;
    }

    public Movie(String title, String year, String genre, String director, String actors) {
        Title = title;
        Year = year;
        Genre = genre;
        Director = director;
        Actors = actors;
    }

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getRuntime() {
        return Runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public String getDirector() {
        return Director;
    }

    public String getType() {
        return Type;
    }

    public String getActors() {
        return Actors;
    }

    public String getPlot() {
        return Plot;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getPoster() {
        return Poster;
    }

    public String getImdbReview() {
        return imdbReview;
    }

}
