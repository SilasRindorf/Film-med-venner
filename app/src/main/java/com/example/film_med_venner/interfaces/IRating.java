package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IRating extends IHomeFeedItems {
    //fx 0-10 stars
    int getRating();
    int getFriendsAverageRating();

    String getReview();
    ArrayList<String> getRatings();
}
