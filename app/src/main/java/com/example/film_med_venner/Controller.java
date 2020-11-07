package com.example.film_med_venner;

import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.databases.DatabaseNonPers;

public class Controller {
    private IDatabase database;
    private static Controller instance;
    private Controller(){
        database = new DatabaseNonPers();
        
    }

    public static Controller getInstance(){
        if (instance == null){
            instance = new Controller();
        }
        return instance;
    }
}
