package com.example.film_med_venner.controllers;

import android.util.Log;

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DTO.ReviewDTO;
import com.example.film_med_venner.interfaces.IController.IController_Review;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewUI;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewsUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller_Review implements IController_Review {
    private static Controller_Review instance;
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuh;
    public static Controller_Review getInstance(){
        if (instance == null){
            instance = new Controller_Review();
        }
        return instance;
    }

    private Controller_Review(){
        db = FirebaseFirestore.getInstance();
        mAuh = FirebaseAuth.getInstance();
    }

    //----------------------------------REVIEWS----------------------------------


    public void updateReviews(IReview rating) throws IDatabase.DatabaseException {
        try {
            db.collection("users")
                    .document(rating.getUserID()).collection("reviews")
                    .document(rating.getMovieIDStr()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    db.collection("reviews").document(task.getResult().getId())
                            .set(new ReviewDTO(rating, new Date()), SetOptions.merge());
                }
            });
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error updating review", e);
        }
    }

    public void createReview(IReview rating) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(rating.getUserID())
                    .collection("reviews")
                    .add(new ReviewDTO(rating, new Date())).addOnCompleteListener(task -> {
                rating.setReviewID(task.getResult().getId());
            });
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error creating review", e);
        }
    }

    public void getReviews(RunnableReviewsUI runnableReviewsUI) throws IDatabase.DatabaseException {
        try {
            db.collection("users")
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
            throw new IDatabase.DatabaseException("Error getting reviews", e);
        }
    }

    public void getReview(String userID, String movieID, RunnableReviewUI runnableReviewUI) throws IDatabase.DatabaseException {
        Log.e("You are here: ", "RIGHT HERE!");
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
                        }
                    });
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error getting reviews", e);
        }
    }

    public void getFriendReviews(RunnableReviewsUI runnableReviewsUI) throws IDatabase.DatabaseException {
        try {

            db.collection("users")
                    .document(mAuh.getCurrentUser().getUid())
                    .collection("friends")
                    .whereEqualTo("status", true)
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        db.collection("users").document(
                                doc.getId()).collection("reviews").get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                //Could be split to multiple lines for easier readability. But I'm lazy
                                runnableReviewsUI.run(task1.getResult().toObjects(ReviewDTO.class).toArray(new ReviewDTO[task1.getResult().size()]));
                            }
                        });
                    }
                }
            });

        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error getting friend reviews", e);
        }
    }

}