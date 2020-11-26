package com.example.film_med_venner.databases;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.Generator;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;


public class DatabaseNonPers implements IDatabase {
    //private HashMap<Enums.Genre[], IMovie> movies;
    private IProfile[] profiles;
    private IMovie[] movies;
    private static IDatabase instance;

    public static IDatabase getInstance() {
        if (instance == null){
            instance = new DatabaseNonPers();
        }
        return instance;
    }

    private DatabaseNonPers(){
        Generator gen = new Generator();
        movies = gen.generateMovies(10);
        profiles = gen.generateProfiles(10);
    }

    @Override
    public IProfile getProfile(int id) {
        //Temporary return ID is tied to a profile not pos in array
        return profiles[id];
    }

    @Override
    public IMovie[] getMoviesWithGenre(String Genre) {
        return null;
    }

    @Override
    public IMovie[] getMovies() {   
        return movies;
    }

    @Override
    public IProfile[] getProfiles() {
        return profiles;
    }
    public IProfile[] getFriends(int ID) {
        IProfile[] friends = new Profile[profiles[ID].getFriends().size()];
        return profiles[ID].getFriends().toArray(friends);
    }


}
