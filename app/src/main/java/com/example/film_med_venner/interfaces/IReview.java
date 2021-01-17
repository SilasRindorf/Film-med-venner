package com.example.film_med_venner.interfaces;
public interface IReview extends IHomeFeedItems {
    //fx 0-10 stars
    int getRating();
    void setReviewID(String id);
    String getReview();
}
