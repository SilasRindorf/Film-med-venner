package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IMovie;

public class Movie implements IMovie {
    private String Title;
    private String Year;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Actors;
    private String Plot;
    private String imdbID;
    private String Poster;
    private String imdbRating;

    public Movie(String title, String year, String runtime, String genre, String director, String actors, String plot, String imdbID, String poster, String imdbRating) {
        Title = title;
        Year = year;
        Runtime = runtime;
        Genre = genre;
        Director = director;
        Actors = actors;
        Plot = plot;
        this.imdbID = imdbID;
        Poster = poster;
        this.imdbRating = imdbRating;
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

    public String getRuntime(){return Runtime;}

    public String getGenre() {
        return Genre;
    }

    public String getDirector() {
        return Director;
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

    public String getImdbRating() {
        return imdbRating;
    }

}
