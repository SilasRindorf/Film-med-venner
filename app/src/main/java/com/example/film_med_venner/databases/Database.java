package com.example.film_med_venner.databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.runnable.RunUI;
import com.example.film_med_venner.runnable.RunnableMovieUI;
import com.example.film_med_venner.runnable.RunnableReviewUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


//TODO should be handled in thread
public class Database implements IDatabase {
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuh;
    private static Database instance;

    private Database() {
        db = FirebaseFirestore.getInstance();
        mAuh = FirebaseAuth.getInstance();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void addUser(String name, String userID) {
        HashMap<String, Object> user = new HashMap();
        user.put("name", name);
        db.collection("users").document(userID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "User added with ID: " + user.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding user", e);
            }
        });
    }


    @Override
    public IProfile getProfile(String id) {
        return null;
    }
    //TODO should be changed current run time is N
    public void getProfile(String id, RunUI runnable) throws DatabaseException {
        //Get all users and check for user with ID id
        try {
            db.collection("users")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                //If the person exists in the database
                                if (doc.getId().equals(id)) {
                                    //Create a Profile
                                    IProfile profile = new Profile(doc.get("name").toString(), doc.getId());
                                    //Run the interface function void run (IProfile)
                                    runnable.run(profile);
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            throw new DatabaseException("Error getting user", e);
        }
    }


    @Override
    public IMovie[] getMoviesWithGenre(String Genre) {
        return new IMovie[0];
    }
    //Doesn't work yet
    public void getMoviesWithGenre(String genre, RunnableMovieUI runnable) throws DatabaseException {
        //Get all movies and check for movies with genrer
        try {
            db.collection("movies")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            ArrayList<IMovie> movies = new ArrayList<IMovie>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                //If the movie has the specified genre
                                if (doc.getData().get("genre").equals(genre)) {
                                    //Create a Movie
                                    ArrayList<String> stringsOK = new ArrayList<>();
                                    doc.getData().get("actors");
                                    //Not the full correct way, missing genre and
                                   Movie movie = new Movie( doc.get("title").toString(),  doc.get("info").toString(),stringsOK, new String[2], doc.get("posterPath").toString());
                                    movies.add(movie);
                                }
                            }
                            IMovie[] mvs =  new Movie[movies.size()];
                            runnable.run(movies.toArray(mvs));
                        }
                    });
        } catch (Exception e) {
            throw new DatabaseException("Error getting moves with " + genre, e);
        }
    }
    @Override
    public IMovie[] getMovies() {
        return new IMovie[0];
    }

    @Override
    public IProfile[] getProfiles() {
        return new IProfile[0];
    }

    @Override
    public ArrayList<IHomeFeedItems> getHomeFeed() {
        return null;
    }

    @Override
    public IReview[] getReviews() {
        return new IReview[0];
    }

    public void getReviews(RunnableReviewUI runnableReviewUI) throws DatabaseException {
        try {
            db.collection("reviews")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            int i = 0;
                            IReview[] reviews = new Review[task.getResult().size()];
                            for (QueryDocumentSnapshot doc : task.getResult()) {


                                ArrayList<String> stringsOK = new ArrayList<>();
                                reviews[i] = new Review((int) doc.get("rating"), doc.get("username").toString(), doc.get("movieID").toString(), doc.get("reviewID").toString(), doc.get("review").toString());
                                i++;
                            }

                            runnableReviewUI.run(reviews);
                        }
                    });
        } catch (Exception e) {
            throw new DatabaseException("Error getting reviews", e);
        }
    }

    @Override
    public IRating[] getRating() {
        return new IRating[0];
    }

    @Override
    public void sendFriendRequest(String id) throws DatabaseException {
        HashMap<String, Object> user = new HashMap();
        String selfID = mAuh.getUid();
        user.put("userID", selfID);
        user.put("requester", db.collection("users").document(selfID));
        user.put("status", null);
        try {
            db.collection("users").document(id).collection("friends").document(selfID)
                    .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "Friend request send to ID: " + id);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error sending friend request", e);
                }
            });
        } catch (Exception e) {
            throw new DatabaseException("Error adding user", e);
        }

    }
}
