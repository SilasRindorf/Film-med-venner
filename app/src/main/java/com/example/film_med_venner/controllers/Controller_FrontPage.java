package com.example.film_med_venner.controllers;

import com.example.film_med_venner.Generator;
import com.example.film_med_venner.interfaces.IController.IFrontPageController;
import com.example.film_med_venner.interfaces.IRating;

public class Controller_FrontPage implements IFrontPageController {
    private IFrontPageController instance;
    private Controller_FrontPage(){

    }

    @Override
    public IRating[] getFriendRatings() {
        return new Generator().generateRatings(10);
    }

    @Override
    public IFrontPageController getInstance() {
        if (instance == null){
            instance = new Controller_FrontPage();
        }
        return instance;
    }
}
