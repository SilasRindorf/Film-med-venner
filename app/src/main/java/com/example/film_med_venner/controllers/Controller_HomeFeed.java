package com.example.film_med_venner.controllers;

import com.example.film_med_venner.Generator;
import com.example.film_med_venner.interfaces.IController.IController_HomeFeed;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;

import java.util.ArrayList;


public class Controller_HomeFeed implements IController_HomeFeed {
    private IDatabase database;
    private static Controller_HomeFeed instance;
    public static Controller_HomeFeed getInstance(){
        if (instance == null){
            instance = new Controller_HomeFeed();
        }
        return instance;
    }

    private Controller_HomeFeed(){
        database = IDatabase.getInstance();
    }

    public ArrayList<IHomeFeedItems> getHomeFeedItems(){return new Generator().generateHomeFeedItems(10);}

}
