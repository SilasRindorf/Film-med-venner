package com.example.film_med_venner.controllers;

import android.net.Uri;
import android.util.Log;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DTO.FriendDTO;
import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.DTO.ProfileDTO;
import com.example.film_med_venner.DTO.ReviewDTO;
import com.example.film_med_venner.DTO.WatchItemDTO;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IWatchItem;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.sentry.Sentry;

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
            return new Profile(user.getDisplayName(), user.getUid());
        } catch (Exception ignored) {
            return null;
        }
    }

    public void getCurrentUserWithmvGPrefs(RunnableProfileUI runnableProfileUI) throws IDatabase.DatabaseException {
        FirebaseUser user = mAuh.getCurrentUser();
        try {
            db.collection("users").document(user.getUid()).get().addOnSuccessListener(documentSnapshot ->
                    runnableProfileUI.run(documentSnapshot.toObject(Profile.class)));
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void getCurrentUserWithmvGPrefs(RunnableProfileUI runnableProfileUI):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Could not get movie preferences", e);
        }
    }

    public String getCurrentUserEmail() {
        FirebaseUser user = mAuh.getCurrentUser();
        try {
            return user.getEmail();
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called String getCurrentUserEmail():  ", e.getMessage());
            return null;
        }
    }

    public void sendPasswordEmail(String email) {
        mAuh.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> { })
                .addOnFailureListener(e -> Sentry.addBreadcrumb("Called void sendPasswordEmail(String email):  ", e.getMessage()));
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
            Sentry.addBreadcrumb("Called void logIn(String email, String password, RunnableUI runnableUI):  ", e.getMessage());
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
                        Sentry.addBreadcrumb("Called void loginWithFacebookUser(AccessToken token, RunnableUI runnableUI)->runnableUI.run():  ", e.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void loginWithFacebookUser(AccessToken token, RunnableUI runnableUI):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error logging in", e);
        }
    }

    public void addUser(IProfile profile, String pictureURL) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(profile.getName())
                .build();
        mAuh.getCurrentUser().updateProfile(profileUpdates);
        ProfileDTO prof = new ProfileDTO(profile);
        if (pictureURL == null)
            prof.setPictureURL("https://cdn2.iconfinder.com/data/icons/facebook-51/32/FACEBOOK_LINE-01-512.png");
        else
            prof.setPictureURL(pictureURL);
        db.collection("users").document(profile.getID()).set(prof)
                .addOnSuccessListener(aVoid -> {})
                .addOnFailureListener(e -> Sentry.addBreadcrumb("Called vvoid addUser(IProfile profile, String pictureURL):  ", e.getMessage()));
    }

    public void createUser(String email, String password, IProfile profile, RunnableErrorUI runnableUI) throws IDatabase.DatabaseException {
        try {
            mAuh.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    mAuh.getCurrentUser().sendEmailVerification();
                    profile.setID(mAuh.getCurrentUser().getUid());
                    profile.setEmail(email);
                    addUser(profile, null);
                    runnableUI.run();
                } else {
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
                        Sentry.addBreadcrumb("Called void createUser(String email, String password, IProfile profile, RunnableErrorUI runnableUI)->runnableUI.run():  ", e.getMessage());
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            Sentry.addBreadcrumb("Called void createUser(String email, String password, IProfile profile, RunnableErrorUI runnableUI):  ", e.getMessage());
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
            facebookProfile.setID(mAuh.getCurrentUser().getUid());
            facebookProfile.setEmail(email);
            addUser(facebookProfile, profilePictureURL);
            runnableUI.run();
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void addFacebookUser(String email, String profilePictureURL, IProfile facebookProfile, RunnableErrorUI runnableUI):  ", e.getMessage());
            runnableUI.handleError(new IDatabase.DatabaseException("Error creating Facebook user", e));
        }

    }

    public void updateUser(String name, String email, String topGenres, RunnableErrorUI runnableUI) throws IDatabase.DatabaseException {
        try {
            Map<String, Object> docData = new HashMap<>();
            docData.put("id", Controller_User.getInstance().getCurrentUser().getID());
            docData.put("name", name);
            docData.put("mvGPrefs", topGenres);
            db.collection("users").document(mAuh.getUid()).set(docData, SetOptions.merge()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    runnableUI.run();
                } else {
                    Sentry.addBreadcrumb("Called void updateUser(String name, String email, String topGenres, RunnableErrorUI runnableUI)->runnableUI.run():  Error updating user");
                }
            });
            mAuh.getCurrentUser().updateEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    runnableUI.run();
                } else {
                    Sentry.addBreadcrumb("Called void updateUser(String name, String email, String topGenres, RunnableErrorUI runnableUI)->runnableUI.run():  Error updating email");
                }
            });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void updateUser(String name, String email, String topGenres, RunnableErrorUI runnableUI):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error updating user",e);
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
                        user.updatePassword(passwordNew);
                    } else {
                        Sentry.addBreadcrumb("Called void updateUserPassword(String passwordOld, String passwordNew, RunnableErrorUI runnableUI):  Error updating password");
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
            db.runTransaction((Transaction.Function<Void>) transaction -> {
                boolean[] checks = {false, false, false, false};
                DocumentReference documentReference = db.collection("users").document(uID);
                FullProfileDTO fullProfileDTO = transaction.get(documentReference).toObject(FullProfileDTO.class);

                db.collection("users").document(uID).collection("friends").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<FriendDTO> friends = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult()) {
                            friends.add(doc.toObject(FriendDTO.class));
                        }
                        try {
                            checks[0] = true;
                            fullProfileDTO.setFriends(friends);
                            check(checks, () -> runnableFullProfileUI.run(fullProfileDTO));
                        } catch (IDatabase.DatabaseException | NullPointerException e) {
                            Sentry.addBreadcrumb("Called void getFullProfile(String uID, RunnableFullProfileUI runnableFullProfileUI)->runnableFullProfileUI.run(fullProfileDTO)->friends:  ", e.getMessage());
                        }
                    }
                });


                db.collection("users").document(uID).collection("reviews").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ReviewDTO> reviews = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult()) {
                            reviews.add(doc.toObject(ReviewDTO.class));
                        }
                        try {
                            checks[1] = true;
                            fullProfileDTO.setReviews(reviews);
                            check(checks, () -> runnableFullProfileUI.run(fullProfileDTO));
                        } catch (IDatabase.DatabaseException | NullPointerException e) {
                            Sentry.addBreadcrumb("Called void getFullProfile(String uID, RunnableFullProfileUI runnableFullProfileUI)->runnableFullProfileUI.run(fullProfileDTO)->reviews:  ", e.getMessage());
                        }
                    }
                });


                db.collection("users").document(uID).collection("watched_list").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<IWatchItem> watched_list = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult()) {
                            watched_list.add(doc.toObject(WatchItemDTO.class));
                        }
                        try {
                            checks[2] = true;
                            fullProfileDTO.setWatchedList(watched_list);
                            check(checks, () -> runnableFullProfileUI.run(fullProfileDTO));
                        } catch (IDatabase.DatabaseException | NullPointerException e) {
                            Sentry.addBreadcrumb("Called void getFullProfile(String uID, RunnableFullProfileUI runnableFullProfileUI)->runnableFullProfileUI.run(fullProfileDTO)->watched_list:  ", e.getMessage());
                        }
                    }
                });


                db.collection("users").document(uID).collection("to_watch_list").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<IWatchItem> to_watch_list = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult()) {
                            to_watch_list.add(doc.toObject(WatchItemDTO.class));
                        }
                        try {
                            checks[3] = true;
                            fullProfileDTO.setToWatchList(to_watch_list);
                            check(checks, () -> runnableFullProfileUI.run(fullProfileDTO));
                        } catch (IDatabase.DatabaseException | NullPointerException e) {
                            Sentry.addBreadcrumb("Called void getFullProfile(String uID, RunnableFullProfileUI runnableFullProfileUI)->runnableFullProfileUI.run(fullProfileDTO)->to_watch_list:  ", e.getMessage());
                        }
                    }
                });
                return null;
            });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void getFullProfile(String uID, RunnableFullProfileUI runnableFullProfileUI)->runnableFullProfileUI.run(fullProfileDTO):  ", e.getMessage());
        }
    }

    private void check(boolean[] checks, RunnableUI runnableUI) throws IDatabase.DatabaseException {
        boolean succeeded = true;
        for (boolean check :
                checks) {
            if (!check) {
                succeeded = false;
                break;
            }
        }
        if (succeeded) {
            runnableUI.run();
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
            Sentry.addBreadcrumb("Called void getProfiles(RunnableProfilesUI runnable):  ", e.getMessage());
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
            Sentry.addBreadcrumb("Called void getProfile(String id, RunnableProfileUI runnable):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error getting user", e);
        }
    }


}
