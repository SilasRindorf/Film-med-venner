package com.example.film_med_venner.databases;

import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;

public class DatabaseNonPers implements IDatabase {
    public DatabaseNonPers(){

    }
    private IProfile[] profiles;

    @Override
    public IProfile getProfile(int id) {
        return null;
    }

    @Override
    public IMovie[] getMovies() {
        return new IMovie[0];
    }
}
