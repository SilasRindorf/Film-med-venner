package com.example.film_med_venner.interfaces.IController;

import com.example.film_med_venner.interfaces.IReview;

public interface IFrontPageController extends IController{
    IReview[] getFriendReviews();
    IFrontPageController getInstance();
}
