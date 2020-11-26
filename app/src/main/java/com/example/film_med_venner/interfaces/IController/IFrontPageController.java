package com.example.film_med_venner.interfaces.IController;

import com.example.film_med_venner.interfaces.IRating;

public interface IFrontPageController extends IController{
    IRating[] getFriendRatings();
    IFrontPageController getInstance();
}
