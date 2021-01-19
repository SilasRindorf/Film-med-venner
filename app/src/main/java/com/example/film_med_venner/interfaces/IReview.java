package com.example.film_med_venner.interfaces;

import java.util.Date;

public interface IReview extends IHomeFeedItems {
    //fx 0-10 stars
    int getRating();
    void setReviewID(String id);
    String getReview();
    Date getCreationDate();
    void setCreationDate(Date creationDate);
}
