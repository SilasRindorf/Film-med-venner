package com.example.film_med_venner.databases;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.Generator;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseNonPers implements IDatabase {
    //private ArrayList<IMovie> movies;
    //private HashMap<ArrayList<String>, IMovie> movies;
    private IMovie[] movies;
    private IProfile[] profiles;

    public DatabaseNonPers(){
        Generator gen = new Generator();
        //movies = new ArrayList<>();
        //movies = new HashMap<>();
        movies = gen.generateMovies(10);


    }

    @Override
    public IProfile getProfile(int id) {
        return null;
    }

    @Override
    public IProfile[] getProfiles() {
        return new IProfile[0];
    }

    @Override
    public IMovie[] getMoviesWithGenre(String Genre) {
        return new IMovie[0];
    }

    @Override
    public IMovie[] getMovies() {
        return movies;
    }
}
