package com.example.film_med_venner.databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DTO.RatingDTO;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableMovieUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfilesUI;
import com.example.film_med_venner.interfaces.runnable.RunnableRatingUI;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Database implements IDatabase {
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuh;
    private static Database instance;


    //Firebase methods  are all async
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

    public IProfile getCurrentUser() {
        FirebaseUser user = mAuh.getCurrentUser();
        return new Profile(user.getDisplayName(), user.getUid());
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

    public void logIn(String email, String password, RunnableUI runnableUI) throws DatabaseException {
        try {
            mAuh.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    runnableUI.run();
                }
            });
        } catch (Exception e) {
            throw new DatabaseException("Error logging in", e);
        }
    }


    @Override
    public IProfile getProfile(String id) {
        return null;
    }

    //TODO should be changed current run time is N
    public void getProfile(String id, RunnableProfileUI runnable) throws DatabaseException {
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

    public void getMoviesWithGenre(String genre, RunnableMovieUI runnable) throws DatabaseException {
        //Get all movies and check for movies with genrer
        try {
            db.collection("movies")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<IMovie> movies = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                //If the movie has the specified genre
                                if (doc.getData().get("genre").equals(genre)) {
                                    //Add a Movie
                                    movies.add(doc.toObject(Movie.class));
                                }
                            }
                            IMovie[] mvs = new Movie[movies.size()];
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

    public void getProfiles(RunnableProfilesUI runnable) throws DatabaseException {
        //Get all users and check for user with ID id
        try {
            db.collection("users")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            /*IProfile[] profiles = new Profile[task.getResult().size()];
                            int i = 0;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                //Create a Profile
                                profiles[i] = new Profile(doc.get("name").toString(), doc.getId());
                                i++;
                            }*/
                            List<Profile> profiles2 = task.getResult().toObjects(Profile.class);
                            //Run the interface function void run (IProfile)
                            IProfile[] profs = new Profile[profiles2.size()];
                            runnable.run(profiles2.toArray(profs));
                        }
                    });
        } catch (Exception e) {
            throw new DatabaseException("Error getting users", e);
        }
    }

    @Override
    public ArrayList<IHomeFeedItems> getHomeFeed() {
        return null;
    }


    public void createUser(String email, String password, String name, RunnableErrorUI runnableUI) throws DatabaseException {
        try {
            mAuh.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Create user with email: Success ");
                    //addUser();
                    runnableUI.run();
                } else {
                    Log.d(TAG, "Create user with email: Failed ");
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        runnableUI.run(new DatabaseException("Weak Password", e, 101));
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        runnableUI.run(new DatabaseException("Invalid Credentials", e, 102));
                    } catch (FirebaseAuthUserCollisionException e) {
                        runnableUI.run(new DatabaseException("User Collision", e, 103));
                    } catch (FirebaseAuthEmailException e) {
                        runnableUI.run(new DatabaseException("User Collision", e, 103));
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }

                }
            });
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Error creating user", e);
        }

    }

    public void createReview(IRating rating) throws DatabaseException {
        try {
            db.collection("reviews")
                    .add(new RatingDTO(rating)).addOnCompleteListener(task -> {
                        rating.setRatingID(task.getResult().getId());
            });
        } catch (Exception e) {
            throw new DatabaseException("Error creating review", e);
        }
    }

    public void getRatings(RunnableRatingUI runnableRatingUI) throws DatabaseException {
        try {
            db.collection("reviews")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Rating> ratings = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Rating crRating  = doc.toObject(Rating.class);
                                crRating.setRatingID(doc.getId());
                                ratings.add(crRating);
                            }
                            IRating[] rats = new Rating[ratings.size()];
                            runnableRatingUI.run(ratings.toArray(rats));
                        }
                    });
        } catch (Exception e) {
            throw new DatabaseException("Error getting reviews", e);
        }
    }

    public void getRating(String ratingID, RunnableRatingUI runnableRatingUI) throws DatabaseException {
        try {
            db.collection("reviews")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                if (doc.getId().equals(ratingID)) {
                                    Rating crRating = doc.toObject(Rating.class);
                                    crRating.setRatingID(doc.getId());
                                    runnableRatingUI.run(crRating);
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            throw new DatabaseException("Error getting reviews", e);
        }
    }
    public void getRating(String userID, String movieID, RunnableRatingUI runnableRatingUI) throws DatabaseException {
        try {
            db.collection("reviews")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                if (doc.get("userID").equals(userID) && doc.get("movieIDStr").equals(movieID)) {
                                    Rating crRating = doc.toObject(Rating.class);
                                    crRating.setRatingID(doc.getId());
                                    runnableRatingUI.run(crRating);
                                }
                            }
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
