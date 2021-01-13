package com.example.film_med_venner.databases;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.runnable.RunnableProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableMovieUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfilesUI;
import com.example.film_med_venner.interfaces.runnable.RunnableRatingsUI;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.example.film_med_venner.ui.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
/* Showcase how to use
        try {
                Database.getInstance().getProfiles(profiles -> {
                });
                } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
                }

                try {
                Database.getInstance().getProfile("wDE5liDVpHdaHaYWBh5wmOKf7O12", profile -> {
                Log.d(TAG, "Hah my namevwwv " + profile.getName());

                });
                //Lambda is overriding the run method
                //Meaning the lambda and this is essentially the same thing
                Database.getInstance().getProfile("wDE5liDVpHdaHaYWBh5wmOKf7O12", new RunnableProfileUI() {
@Override
public void run(IProfile profile) {

        }


        });
        } catch (IDatabase.DatabaseException e) {
        e.printStackTrace();
        }*/


//TODO should be handled in thread
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
        return new Profile(user.getDisplayName(),user.getUid());
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

    //Doesn't work yet
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


    public void createReview(IRating rating) throws DatabaseException {
        //Get all users and check for user with ID id
        try {
            db.collection("reviews")
                    .add(rating);
        } catch (Exception e) {
            throw new DatabaseException("Error creating review", e);
        }
    }

    public void getReviews(RunnableRatingsUI runnableRatingsUI) throws DatabaseException {
        try {
            db.collection("reviews")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            int i = 0;
                            List<Rating> ratings = task.getResult().toObjects(Rating.class);
                            /*for (QueryDocumentSnapshot doc : task.getResult()) {
                                ArrayList<String> stringsOK = new ArrayList<>();
                                ratings[i] = new Rating((int) doc.get("rating"), doc.get("username").toString(), doc.get("movieID").toString(), doc.get("reviewID").toString(), doc.get("review").toString());
                                i++;
                            }*/
                            IRating[] rats = new Rating[ratings.size()];
                            runnableRatingsUI.run(ratings.toArray(rats));
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
