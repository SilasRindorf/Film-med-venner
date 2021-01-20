package com.example.film_med_venner.controllers;

import android.util.Log;

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DTO.ReviewDTO;
import com.example.film_med_venner.interfaces.IController.IController_Review;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewUI;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewsLoadUI;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewsUI;
import com.example.film_med_venner.interfaces.runnable.RunnableStringUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.sentry.Sentry;

public class Controller_Review implements IController_Review {
    private static Controller_Review instance;
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuh;

    private Controller_Review() {
        db = FirebaseFirestore.getInstance();
        mAuh = FirebaseAuth.getInstance();
    }

    public static Controller_Review getInstance() {
        if (instance == null) {
            instance = new Controller_Review();
        }
        return instance;
    }

    //----------------------------------REVIEWS----------------------------------

    public void updateReview(IReview rating) throws IDatabase.DatabaseException {
        try {
            db.collection("users")
                    .document(rating.getUserID()).collection("reviews")
                    .document(rating.getMovieIDStr()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    db.collection("users").document(rating.getUserID()).collection("reviews").document(rating.getMovieIDStr())
                            .set(new ReviewDTO(rating, new Date()), SetOptions.merge()).addOnCompleteListener(task1 -> {

                    });
                }
            });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void updateReview(IReview rating):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error updating review", e);
        }
    }

    public void createReview(IReview rating) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(rating.getUserID())
                    .collection("reviews").document(rating.getMovieIDStr())
                    .set(new ReviewDTO(rating, new Date())).addOnCompleteListener(task -> {
            });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void createReview(IReview rating):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error creating review", e);
        }
    }

    public void getReviews(String userID, RunnableReviewsUI runnableReviewsUI) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(userID).collection("reviews")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Review> ratings = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Review crReview = doc.toObject(Review.class);
                                crReview.setReviewID(doc.getId());
                                ratings.add(crReview);
                            }
                            IReview[] rats = new Review[ratings.size()];
                            runnableReviewsUI.run(ratings.toArray(rats));
                        }
                    });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void getReviews(String userID, RunnableReviewsUI runnableReviewsUI):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error getting reviews", e);
        }
    }

    public void getReview(String reviewID, RunnableReviewUI runnableReviewUI) throws IDatabase.DatabaseException {
        try {
            db.collection("users")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                try {
                                    db.collection("users")
                                            .document(doc.getId()).collection("reviews")
                                            .document(reviewID).get().addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Review crReview = doc.toObject(Review.class);
                                            crReview.setReviewID(doc.getId());
                                            runnableReviewUI.run(crReview);
                                        }
                                    });
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void getReview(String reviewID, RunnableReviewUI runnableReviewUI):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error getting reviews", e);
        }
    }

    public void getReview(String userID, String movieID, RunnableReviewUI runnableReviewUI) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(userID)
                    .collection("reviews")
                    .whereEqualTo("movieIDStr", movieID)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Review crReview = doc.toObject(Review.class);
                                crReview.setReviewID(doc.getId());
                                runnableReviewUI.run(crReview);
                            }
                        } else {
                            runnableReviewUI.run();
                        }
                    });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void getReview(String userID, String movieID, RunnableReviewUI runnableReviewUI):  ", e.getMessage());
            runnableReviewUI.run();
            throw new IDatabase.DatabaseException("Error getting reviews", e);
        }
    }

    public void getFriendsWhoReviewed(String movieIDStr, RunnableStringUI runnableStringUI) throws IDatabase.DatabaseException {
        try {
            db.collection("users")
                    .document(mAuh.getCurrentUser().getUid())
                    .collection("friends")
                    .whereEqualTo("status", 1)
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        runnableStringUI.run((doc.getId()));
                    }
                }
            });


        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void getFriendsWhoReviewed(String movieIDStr, RunnableStringUI runnableStringUI):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error getting friend reviews", e);
        }
    }

    //Doesn't work
    public void getFriendReviews(String movieIDStr, RunnableReviewsLoadUI runnableReviewsLoadUI) throws IDatabase.DatabaseException {
        try {

            db.collection("users")
                    .document(mAuh.getCurrentUser().getUid())
                    .collection("friends")
                    .whereEqualTo("status", 1)
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //For each friend
                    for (DocumentSnapshot doc : task.getResult()) {
                        //That has reviewed movieIDStr
                        db.collection("users").document(
                                doc.getId()).collection("reviews").whereEqualTo("movieIDStr", movieIDStr).orderBy("creationDate")
                                .get().addOnCompleteListener(task1 -> {
                            //If we get
                            if (task1.isSuccessful()) {
                                ReviewDTO reviewDTO = task1.getResult().toObjects(ReviewDTO.class).toArray(new ReviewDTO[task1.getResult().size()])[0];
                                //ADD TO LIST
                            } else {
                                runnableReviewsLoadUI.run();
                            }
                        });
                    }
                } else {
                    runnableReviewsLoadUI.run();
                }
            });

        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void getFriendReviews(String movieIDStr, RunnableReviewsLoadUI runnableReviewsLoadUI):  ", e.getMessage());
            runnableReviewsLoadUI.run();
            throw new IDatabase.DatabaseException("Error getting friend reviews", e);
        }
    }

    public IReview reviewDTOtoIReview(ReviewDTO reviewDTO) {
        return new Review(reviewDTO.getRating(),
                reviewDTO.getUsername(), reviewDTO.getMovieIDStr(),
                reviewDTO.getReview(), reviewDTO.getUserID());
    }

    public IReview[] reviewDTOtoIReview(ReviewDTO[] reviewDTOs) {
        IReview[] reviews = new Review[reviewDTOs.length];
        for (int i = 0; i < reviewDTOs.length; i++) {
            reviews[i] = new Review(reviewDTOs[i].getRating(),
                    reviewDTOs[i].getUsername(), reviewDTOs[i].getMovieIDStr(),
                    reviewDTOs[i].getReview(), reviewDTOs[i].getUserID());
        }
        return reviews;
    }

}