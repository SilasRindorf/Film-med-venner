package com.example.film_med_venner.DTO;

import com.example.film_med_venner.interfaces.IReview;

import java.util.List;

public class FullProfileDTO extends ProfileDTO {
    List<ProfileDTO> friends;
    List<ReviewDTO> reviews;
    String profilePicture;


    public List<ProfileDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<ProfileDTO> friends) {
        this.friends = friends;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}
