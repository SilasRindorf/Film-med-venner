package com.example.film_med_venner.controllers;

import com.example.film_med_venner.Generator;
import com.example.film_med_venner.databases.DatabaseNonPers;
import com.example.film_med_venner.interfaces.IController.IFrontPageController;
import com.example.film_med_venner.interfaces.IReview;

public class Controller_FrontPage implements IFrontPageController {
    private IFrontPageController instance;
    private Controller_FrontPage(){

    }

    @Override
    public IReview[] getFriendReviews() {
        return DatabaseNonPers.getInstance().getReview();
    }

    @Override
    public IFrontPageController getInstance() {
        if (instance == null){
            instance = new Controller_FrontPage();
        }
        return instance;
    }
}
