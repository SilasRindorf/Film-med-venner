package com.example.film_med_venner.controllers;

import com.example.film_med_venner.interfaces.IController.IFrontPageController;
import com.example.film_med_venner.interfaces.IRating;

public class FrontPageController implements IFrontPageController {
    private IFrontPageController instance;
    private FrontPageController(){

    }

    @Override
    public IRating[] getFriendRatings() {
        return new IRating[0];
    }

    @Override
    public IFrontPageController getInstance() {
        if (instance == null){
            instance = new FrontPageController();
        }
        return instance;
    }
}
