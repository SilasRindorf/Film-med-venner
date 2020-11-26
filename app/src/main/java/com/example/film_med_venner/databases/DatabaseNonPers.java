package com.example.film_med_venner.databases;

import android.provider.ContactsContract;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.Generator;
import com.example.film_med_venner.enums.Enums;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;

import java.util.HashMap;

public class DatabaseNonPers implements IDatabase {
    private HashMap<Enums.Genre[], IMovie> movies;
    private IProfile[] profiles;
    private static IDatabase instance;

    public static IDatabase getInstance() {
        if (instance == null){
            instance = new DatabaseNonPers();
        }
        return instance;
    }

    private DatabaseNonPers(){
        Generator gen = new Generator();
        profiles = new Profile[10];
        movies = new HashMap<>();
        IMovie[] genMovies = gen.generateMovies(10);
        profiles = gen.generateProfiles(10);
        for (IMovie mov : genMovies){
            //movies.put(mov.getGenres(),mov);
        }
    }

    @Override
    public IProfile getProfile(int id) {
        //Temporary return ID is tied to a profile not pos in array
        return profiles[id];
    }

    @Override
    public IProfile[] getProfiles() {
        return profiles;
    }

    @Override
    public IMovie[] getMoviesWithGenre(String Genre) {
        return null;
    }

    @Override
    public IMovie[] getMovies() {   
        System.out.println(movies.entrySet().toArray().toString());
        return null;
    }

    @Override
    public IProfile[] getFriends() {
        return new IProfile[0];
    }


}
