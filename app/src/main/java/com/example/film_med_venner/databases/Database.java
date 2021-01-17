package com.example.film_med_venner.databases;

import android.net.Uri;
import android.util.Log;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.DTO.ProfileDTO;
import com.example.film_med_venner.DTO.ReviewDTO;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.IWatchItem;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.interfaces.runnable.RunnableFullProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableMovieUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfilesUI;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewUI;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewsUI;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Database implements IDatabase {
    private static Database instance;
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuh;


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


    //----------------------------------MOVIES----------------------------------


    @Override
    public IProfile getProfile(String id) {
        return null;
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


    //----------------------------------PROFILES----------------------------------
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

   /* @Override
    public IProfile getProfile(String id) {
        return null;
    }*/

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
                                    Log.e("test", "du er her");
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


    //----------------------------------HOME FEED----------------------------------

    @Override
    public ArrayList<IHomeFeedItems> getHomeFeed() {
        return null;
    }


    //----------------------------------USERS----------------------------------
    public IProfile getCurrentUser() {
        FirebaseUser user = mAuh.getCurrentUser();
        try {
            return new Profile(user.getDisplayName(), user.getUid());
        } catch (Exception ignored) {
            return null;
        }
    }


    public void getCurrentUser(RunnableFullProfileUI runnableFullProfileUI) {
        FirebaseUser user = mAuh.getCurrentUser();
        try {
            db.collection("users").document(user.getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FullProfileDTO fullProfileDTO = task.getResult().toObject(FullProfileDTO.class);
                    Thread newThread = new Thread(() -> {
                        db.collection("users")
                                .document(user.getUid()).collection("friends")
                                .get().addOnCompleteListener(task1 -> {
                            fullProfileDTO.setFriends(task1.getResult().toObjects(ProfileDTO.class));
                        });
                        db.collection("users")
                                .document(user.getUid()).collection("reviews")
                                .get().addOnCompleteListener(task1 -> {
                            fullProfileDTO.setReviews(task1.getResult().toObjects(ReviewDTO.class));
                        });
                        runnableFullProfileUI.run(fullProfileDTO);
                    });
                    newThread.start();
                }
            });
        } catch (Exception ignored) {
        }
    }

    public void sendPasswordEmail(String email) {
        mAuh.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Sent email for resetting password"))
                .addOnFailureListener(e -> Log.w(TAG, "Error sending email ", e));
    }

    public void addUser(IProfile profile) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(profile.getName())
                .build();
        mAuh.getCurrentUser().updateProfile(profileUpdates);

        db.collection("users").document(profile.getID()).set(new ProfileDTO(profile))
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User added with ID: " + profile.getID()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding user", e));
    }

    public void logIn(String email, String password, RunnableUI runnableUI) throws DatabaseException {
        try {
            mAuh.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    try {
                        runnableUI.run();
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            throw new DatabaseException("Error logging in", e);
        }
    }

    public void logOut(RunnableUI runnableUI) throws DatabaseException {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        runnableUI.run();

    }

    public void loginWithFacebookUser(AccessToken token, RunnableUI runnableUI) throws DatabaseException {
        try {
            AuthCredential authCredential = FacebookAuthProvider.getCredential(token.getToken());

            mAuh.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    try {
                        runnableUI.run();
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            throw new DatabaseException("Error logging in", e);
        }
    }

    public void createUser(String email, String password, IProfile profile, RunnableErrorUI runnableUI) throws DatabaseException {
        try {
            mAuh.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    mAuh.getCurrentUser().sendEmailVerification();
                    Log.d(TAG, "Create user with email: Success ");
                    profile.setID(mAuh.getCurrentUser().getUid());
                    addUser(profile);
                    runnableUI.run();
                } else {
                    Log.d(TAG, "Create user with email: Failed ");
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        runnableUI.handleError(new DatabaseException("Weak Password", e, 101));
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        runnableUI.handleError(new DatabaseException("Invalid Credentials", e, 102));

                    } catch (FirebaseAuthUserCollisionException e) {
                        runnableUI.handleError(new DatabaseException("User Collision", e, 103));
                    } catch (FirebaseAuthEmailException e) {
                        runnableUI.handleError(new DatabaseException("Invalid email", e, 104));
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }

                }
            });
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Error creating user", e);
        }

    }

    public void addFacebookUser(String email, String profilePictureURL, IProfile facebookProfile, RunnableErrorUI runnableUI) {
        try {
            FirebaseUser user = mAuh.getCurrentUser();
            user.updateEmail(email);

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(facebookProfile.getName())
                    .setPhotoUri(Uri.parse(profilePictureURL))
                    .build();
            mAuh.getCurrentUser().updateProfile(profileUpdates);
            ProfileDTO u = new ProfileDTO(facebookProfile);
            u.setPictureURL(profilePictureURL);
            db.collection("users")
                    .document(mAuh.getCurrentUser().getUid()).set(u)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Map<String, Object> data = new HashMap<>();
                            data.put("id", mAuh.getCurrentUser().getUid());
                            data.put("pictureURL", mAuh.getCurrentUser().getUid());
                            db.collection("users").document(mAuh.getUid()).set(data, SetOptions.merge());
                            runnableUI.run();
                        }
                    });
        } catch (Exception e) {
            runnableUI.handleError(new DatabaseException("Error creating Facebook user", e));
        }

    }

    // TODO Det her skal implementeres for at brugeren kan ændre på sig selv fra settings activity.
    public void updateUser(String name, String email, String topGenres, String password, RunnableErrorUI runnableUI) throws DatabaseException {
        try {
            Map<String, Object> docData = new HashMap<>();
            docData.put("name", name);
            docData.put("topGenres", topGenres);
            db.collection("users").document(mAuh.getUid()).set(docData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    runnableUI.run();
                    Log.d(TAG, "Error happened in updating name or top genres");
                }
            });

            mAuh.getCurrentUser().updatePassword(password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    runnableUI.run();
                } else {
                    Log.d(TAG, "Error happened in updating password");
                }
            });

            mAuh.getCurrentUser().updateEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    runnableUI.run();
                } else {
                    Log.d(TAG, "Error happened in updating email");
                }
            });

        } catch (Exception ignored) {

        }
    }

    public boolean isFacebookUserLoginValid() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }


    //----------------------------------RATINGS----------------------------------


    public void updateReviews(IReview rating) throws DatabaseException {
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
            throw new DatabaseException("Error updating review", e);
        }
    }

    public void createReview(IReview rating) throws DatabaseException {
        try {
            db.collection("users").document(rating.getUserID())
                    .collection("reviews")
                    .add(new ReviewDTO(rating, new Date())).addOnCompleteListener(task -> {
                rating.setReviewID(task.getResult().getId());
            });
        } catch (Exception e) {
            throw new DatabaseException("Error creating review", e);
        }
    }

    public void getReviews(RunnableReviewsUI runnableReviewsUI) throws DatabaseException {
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
            throw new DatabaseException("Error getting reviews", e);
        }
    }

    public void getReview(String reviewID, RunnableReviewUI runnableReviewUI) throws DatabaseException {
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
            throw new DatabaseException("Error getting reviews", e);
        }
    }

    public void getReview(String userID, String movieID, RunnableReviewUI runnableReviewUI) throws DatabaseException {
        Log.e("You are here: ", "RIGHT HERE!");
        try {
            db.collection("users").document(userID)
                    .collection("reviews")
                    .whereEqualTo("movieIDStr",movieID)
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
            throw new DatabaseException("Error getting reviews", e);
        }
    }

    public void getFriendReviews(RunnableReviewsUI runnableReviewsUI) throws DatabaseException {
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
            throw new DatabaseException("Error getting friend reviews", e);
        }
    }


    @Override
    public IReview[] getReview() {
        return new IReview[0];
    }

    //----------------------------------WATCHLIST----------------------------------
    public void addToWatchList(IWatchItem watchItem) throws DatabaseException {
        //TODO Something like a dis?
        /*try {
            db.collection("watchList").add(new WatchItem()).addOnCompleteListener(task -> {
                watchItem.
            });
        } catch (Exception e) {
            throw new DatabaseException("Error creating review", e);
        }
         */
    }

    //----------------------------------FRIENDS----------------------------------
    @Override
    public void sendFriendRequest(String id) throws DatabaseException {
        HashMap<String, Object> user = new HashMap<>();
        String selfID = mAuh.getCurrentUser().getUid();
        user.put("userID", selfID);
        user.put("requester", db.collection("users").document(selfID));
        user.put("status", null);
        try {
            db.collection("users").document(id).collection("friends").document(selfID)
                    .set(user).addOnSuccessListener(aVoid ->
                    Log.d(TAG, "Friend request send to ID: " + id))
                    .addOnFailureListener(e ->
                            Log.w(TAG, "Error sending friend request", e));
        } catch (Exception e) {
            throw new DatabaseException("Error adding friend", e);
        }

    }

    public void getFriendRequests(RunnableProfilesUI runnableUI) throws DatabaseException {
        String id = mAuh.getCurrentUser().getUid();

        try {
            db.collection("users").document(id).collection("friends").whereEqualTo("status", null).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    IProfile[] friends = new Profile[task.getResult().size()];
                    List<Profile> friendz = task.getResult().toObjects(Profile.class);
                    runnableUI.run(friendz.toArray(friends));
                }
            });

        } catch (Exception e) {
            throw new DatabaseException("Error getting friend request", e);
        }
    }

    public void respondToFriendRequest(String friendID, boolean accept, RunnableUI runnableUI) throws DatabaseException {
        HashMap<String, Object> status = new HashMap<>();
        String selfID = mAuh.getCurrentUser().getUid();
        status.put("status", accept);
        try {
            db.collection("users").document(selfID).collection("friends").document(friendID).set(status, SetOptions.merge()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    db.collection("users").document(friendID).collection("friends").document(selfID).set(status, SetOptions.merge());
                    try {
                        runnableUI.run();
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            throw new DatabaseException("Error getting friend request", e);
        }
    }

    public void getFriends(RunnableProfilesUI runnableUI) throws DatabaseException {
        String id = mAuh.getCurrentUser().getUid();
        //TODO Skal der ikke være et friendship ID ift. den path den lige ligesom tager? Jeg ser umiddelbart at at stykket mellem collectionpath "friends" og status tingelingen der er der et doc id som ikke bliver hentet
        //Doc ID'et er friendship ID'et
        String friendshipID;
        //IE String friendshipID = doc.getID() //Fra et for each loop ( for (DocumentSnapshot doc : task.getResult().getDocuments()) )
        try {
            db.collection("users").document(id).collection("friends").whereEqualTo("status", true).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    IProfile[] friends = new Profile[task.getResult().size()];
                    List<Profile> friendz = task.getResult().toObjects(Profile.class);
                    runnableUI.run(friendz.toArray(friends));
                }
            });

        } catch (Exception e) {
            throw new DatabaseException("Error getting friends", e);
        }
    }

}
