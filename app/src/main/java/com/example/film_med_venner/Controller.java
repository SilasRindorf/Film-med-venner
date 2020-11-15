package com.example.film_med_venner;

import com.example.film_med_venner.interfaces.IControl;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.databases.DatabaseNonPers;
import com.example.film_med_venner.interfaces.IMovie;

public class Controller implements IControl {
    private IDatabase database;
    private static Controller instance;
    public static Controller getInstance(){
        if (instance == null){
            instance = new Controller();
        }
        return instance;
    }

    private Controller(){
        database = new DatabaseNonPers();

    }

    public IMovie[] getMovies(){return null;}
}
