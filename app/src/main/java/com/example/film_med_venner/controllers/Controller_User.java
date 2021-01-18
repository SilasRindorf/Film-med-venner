package com.example.film_med_venner.controllers;

import android.net.Uri;
import android.util.Log;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.DTO.ProfileDTO;
import com.example.film_med_venner.DTO.ReviewDTO;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.interfaces.runnable.RunnableFullProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfilesUI;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class Controller_User implements IController {
    private static Controller_User instance;
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuh;

    private Controller_User() {
        db = FirebaseFirestore.getInstance();
        mAuh = FirebaseAuth.getInstance();
    }

    public static Controller_User getInstance() {
        if (instance == null) {
            instance = new Controller_User();
        }
        return instance;
    }


    //----------------------------------USERS----------------------------------
    public IProfile getCurrentUser() {
        FirebaseUser user = mAuh.getCurrentUser();
        try {
            return new Profile(user.getDisplayName(), user.getUid(), db.collection("users").document(user.getUid()).get(Source.valueOf("mvGPrefs")).toString());
        } catch (Exception ignored) {
            return null;
        }
    }

    public String getCurrentUserEmail() {
        FirebaseUser user = mAuh.getCurrentUser();
        try {
            return  user.getEmail();
        } catch (Exception ignored) {
            return null;
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
        ProfileDTO prof = new ProfileDTO(profile);
        prof.setPictureURL("https://cdn2.iconfinder.com/data/icons/facebook-51/32/FACEBOOK_LINE-01-512.png");
        db.collection("users").document(profile.getID()).set(prof)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User added with ID: " + profile.getID()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding user", e));
    }

    public void logIn(String email, String password, RunnableUI runnableUI) throws IDatabase.DatabaseException {
        try {
            mAuh.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    try {
                        runnableUI.run();
                    } catch (IDatabase.DatabaseException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error logging in", e);
        }
    }

    public void logOut(RunnableUI runnableUI) throws IDatabase.DatabaseException {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        runnableUI.run();

    }

    public void loginWithFacebookUser(AccessToken token, RunnableUI runnableUI) throws IDatabase.DatabaseException {
        try {
            AuthCredential authCredential = FacebookAuthProvider.getCredential(token.getToken());

            mAuh.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    try {
                        runnableUI.run();
                    } catch (IDatabase.DatabaseException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error logging in", e);
        }
    }

    public void createUser(String email, String password, IProfile profile, RunnableErrorUI runnableUI) throws IDatabase.DatabaseException {
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
                        runnableUI.handleError(new IDatabase.DatabaseException("Weak Password", e, 101));
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        runnableUI.handleError(new IDatabase.DatabaseException("Invalid Credentials", e, 102));

                    } catch (FirebaseAuthUserCollisionException e) {
                        runnableUI.handleError(new IDatabase.DatabaseException("User Collision", e, 103));
                    } catch (FirebaseAuthEmailException e) {
                        runnableUI.handleError(new IDatabase.DatabaseException("Invalid email", e, 104));
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }

                }
            });
        } catch (IllegalArgumentException e) {
            throw new IDatabase.DatabaseException("Error creating user", e);
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
                            data.put("pictureURL", profilePictureURL);
                            db.collection("users").document(mAuh.getUid()).set(data, SetOptions.merge());
                            runnableUI.run();
                        }
                    });
        } catch (Exception e) {
            runnableUI.handleError(new IDatabase.DatabaseException("Error creating Facebook user", e));
        }

    }

    public void updateUser(String name, String email, String topGenres, RunnableErrorUI runnableUI) throws IDatabase.DatabaseException {
        try {
            Map<String, Object> docData = new HashMap<>();
            docData.put("id", Controller_User.getInstance().getCurrentUser().getID());
            docData.put("name", name);
            docData.put("mvGPrefs", topGenres);
            db.collection("users").document(mAuh.getUid()).set(docData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    runnableUI.run();
                } else {
                    Log.d(TAG, "Error happened in updating name or top genres");
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

    public void updateUserPassword(String passwordOld, String passwordNew, RunnableErrorUI runnableUI) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), passwordOld);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        runnableUI.run();
                        Log.d(TAG, "User re-authenticated.");
                        user.updatePassword(passwordNew);
                    } else {
                        Log.d(TAG, "Error moine froiund");
                    }

                });

    }

    public boolean isFacebookUserLoginValid() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }

    public void getCurrentUser(RunnableFullProfileUI runnableFullProfileUI) {
        getFullProfile(mAuh.getCurrentUser().getUid(), runnableFullProfileUI);
    }

    public void getFullProfile(String uID, RunnableFullProfileUI runnableFullProfileUI) {
        try {
            db.collection("users").document(uID).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FullProfileDTO fullProfileDTO = task.getResult().toObject(FullProfileDTO.class);
                    //No  need to be in a thread
                    Thread newThread = new Thread(() -> {
                        db.collection("users")
                                .document(uID).collection("friends")
                                .get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                fullProfileDTO.setFriends(task1.getResult().toObjects(ProfileDTO.class));
                                db.collection("users")
                                        .document(uID).collection("reviews")
                                        .get().addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        fullProfileDTO.setReviews(task2.getResult().toObjects(ReviewDTO.class));
                                        runnableFullProfileUI.run(fullProfileDTO);
                                    }
                                });
                            }
                        });
                    });
                    newThread.start();
                }
            });
        } catch (Exception ignored) {
        }
    }

    public void getProfiles(RunnableProfilesUI runnable) throws IDatabase.DatabaseException {
        try {
            db.collection("users")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Profile> profiles2 = task.getResult().toObjects(Profile.class);
                            //Run the interface function void run (IProfile[])
                            IProfile[] profs = new Profile[profiles2.size()];
                            runnable.run(profiles2.toArray(profs));
                        }
                    });
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error getting users", e);
        }
    }


    //TODO should be changed current run time is N
    public void getProfile(String id, RunnableProfileUI runnable) throws IDatabase.DatabaseException {
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
            throw new IDatabase.DatabaseException("Error getting user", e);
        }
    }


}
