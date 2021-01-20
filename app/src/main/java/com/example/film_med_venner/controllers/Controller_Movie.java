package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.interfaces.IController.IController_Movie;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.runnable.RunnableMovieUI;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Controller_Movie implements IController_Movie {
    private static Controller_Movie instance;
    private final FirebaseFirestore db;

    private Controller_Movie() {
        db = FirebaseFirestore.getInstance();
    }

    public static Controller_Movie getInstance() {
        if (instance == null) {
            instance = new Controller_Movie();
        }
        return instance;
    }
    //----------------------------------MOVIES----------------------------------

    public void getMoviesWithGenre(String genre, RunnableMovieUI runnable) throws IDatabase.DatabaseException {
        //Get all movies and check for movies with genrer
        try {
            db.collection("movies")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<IMovie> movies = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                //If the movie has the specified genre
                                if (doc.getData().get("genre").equals(genre)) {
                                    //Add a Movie
                                    movies.add(doc.toObject(Movie.class));
                                }
                            }
                            IMovie[] mvs = new Movie[movies.size()];
                            runnable.run(movies.toArray(mvs));
                        }
                    });
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error getting moves with " + genre, e);
        }
    }

}
