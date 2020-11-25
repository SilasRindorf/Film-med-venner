package com.example.film_med_venner.controllers;


import com.example.film_med_venner.interfaces.IController.IProfileController;

public class ProfileController implements IProfileController {
    private static ProfileController instance;
    public static ProfileController getInstance(){
        if (instance == null){
            instance = new ProfileController();
        }
        return instance;
    }
}
