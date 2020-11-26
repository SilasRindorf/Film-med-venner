package com.example.film_med_venner;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;

public class Generator {

    public IMovie[] generateMovies(int amount){
        IMovie[] movies = new Movie[amount];
        for (int i = 0; i < amount; i++) {
            Movie curMov  = new Movie("Movie " + i,
                    "",
                    null,
                    null,
                    "posterPath"
                    );
            movies[i] = curMov;
        }
        return movies;
    }
    public IProfile[] generateProfiles(int amount){
        IProfile[] profiles = new Profile[amount];
        for (int i = 0; i < amount; i++) {
            IProfile prof  = new Profile("Profile " + i, i, i, i, i, i, i);
            profiles[i] = prof;
        }
        return profiles;
    }
}
