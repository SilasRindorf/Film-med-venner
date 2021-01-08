package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IReview extends IRating {
    //Review reason. ie "I rated this movie because..."
    String getReview();
    ArrayList<String> getReviews();
}
