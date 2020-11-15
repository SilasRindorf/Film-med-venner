package com.example.film_med_venner;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.interfaces.IMovie;

public class Generator {
    public IMovie[] generateMovies(int amount){
        IMovie[] movies = new Movie[amount];
        for (int i = 0; i < amount; i++) {
            Movie curMov  = new Movie("Movie " + 1,
                    "",
                    null,
                    null,
                    "posterPath"
                    );
            movies[i] = curMov;
        }
        return movies;
    }
}
