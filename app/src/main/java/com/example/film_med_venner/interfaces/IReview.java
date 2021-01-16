package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IReview extends IHomeFeedItems {
    //fx 0-10 stars
    int getRating();
    int getFriendsAverageReview();
    void setReviewID(String id);
    String getReview();
    ArrayList<String> getReviews();
}
