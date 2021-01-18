package com.example.film_med_venner.controllers;

import com.example.film_med_venner.interfaces.IController.IFrontPageController;

public class Controller_FrontPage implements IFrontPageController {
    private IFrontPageController instance;
    private Controller_FrontPage(){

    }


    @Override
    public IFrontPageController getInstance() {
        if (instance == null){
            instance = new Controller_FrontPage();
        }
        return instance;
    }
}
