package com.example.film_med_venner.controllers;

import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IController.IController;



public class Controller_Auth implements IController {
    private static Controller_Auth instance;


    private Controller_Auth() {

    }

    public static Controller_Auth getInstance() {
        if (instance == null) {
            instance = new Controller_Auth();
        }
        return instance;
    }

    public void addUser(String name, String userID) {
        Database.getInstance().addUser(name, userID);
    }


}
