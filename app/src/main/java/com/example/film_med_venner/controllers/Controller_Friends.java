package com.example.film_med_venner.controllers;


import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.interfaces.runnable.RunnableFullProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfilesUI;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;

import io.sentry.Sentry;

public class Controller_Friends implements IProfileController {
    private static Controller_Friends instance;
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuh;

    private Controller_Friends() {
        db = FirebaseFirestore.getInstance();
        mAuh = FirebaseAuth.getInstance();
    }

    public static Controller_Friends getInstance() {
        if (instance == null) {
            instance = new Controller_Friends();
        }

        return instance;
    }


    //----------------------------------FRIENDS----------------------------------

    /**
     * Send a request to the database to add a friend
     *
     * @param friendID friends ID string
     * @throws FriendException IDatabase exception
     */
    public void sendFriendRequest(String friendID) throws FriendException {
        //If user is oneself
        if (friendID.equals(mAuh.getCurrentUser().getUid()))
            throw new FriendException("Can't add yourself as friend");
        //Create request
        HashMap<String, Object> user = new HashMap<>();
        String selfID = mAuh.getCurrentUser().getUid();
        user.put("requester", selfID);
        user.put("status", 0);
        //Add friend
        try {
            db.collection("users").document(friendID).collection("friends").document(selfID)
                    .set(user, SetOptions.merge());
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called  void sendFriendRequest(String friendID):  ", e.getMessage());
            throw new FriendException("Error adding friend", e);
        }

    }

    /**
     * Send a request to the database to add a friend
     * On Exception run handleError from RunnableErrorUI interface
     *
     * @param email friends email string
     */
    public void sendFriendRequestByMail(String email, RunnableErrorUI runnableErrorUI) {
        HashMap<String, Object> user = new HashMap<>();
        String selfID = mAuh.getCurrentUser().getUid();
        user.put("requester", selfID);
        user.put("status", 0);
        try {
            db.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        if (doc.getId().equals(mAuh.getCurrentUser().getUid()))
                            return;
                        else {
                            db.collection("users").document(doc.getId()).collection("friends").document(selfID)
                                    .set(user, SetOptions.merge())
                                    .addOnSuccessListener(aVoid -> {
                                    })
                                    .addOnFailureListener(e -> {
                                        Sentry.addBreadcrumb("Called  void sendFriendRequestByMail(String email, RunnableErrorUI runnableErrorUI)->addOnFailureListener:  ", e.getMessage());
                                    });
                        }
                    }
                }
            });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called  void sendFriendRequestByMail(String email, RunnableErrorUI runnableErrorUI):  ", e.getMessage());
            runnableErrorUI.handleError(new IDatabase.DatabaseException("Error adding friend", e));
        }
    }

    /**
     * Send a friend request to a profile identified by email
     * If a person request the same email as friend it updates the request
     *
     * @param email email "ex@ex.ex" needs to be a valid email
     * @throws FriendException if profile with email is oneself
     */
    public void sendFriendRequestByMail(String email) throws FriendException {
        //Create request status 0 for friend request
        HashMap<String, Object> user = new HashMap<>();
        String selfID = mAuh.getCurrentUser().getUid();
        user.put("requester", selfID);
        user.put("status", 0);
        try {
            //Find user by email
            //Use Firebase to look through docs
            db.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(task -> {
                //Since we don't know doc ID we
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        //If email identifies oneself
                        if (doc.getId().equals(selfID))
                            return;
                        else {
                            db.collection("users").document(doc.getId()).collection("friends").document(selfID)
                                    .set(user, SetOptions.merge())
                                    .addOnSuccessListener(aVoid -> {
                                    })
                                    .addOnFailureListener(e -> {
                                        Sentry.addBreadcrumb("Called  void sendFriendRequestByMail(String email)->addOnFailureListener:  ", e.getMessage());
                                    });
                        }
                    }
                }
            });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called  void sendFriendRequestByMail(String email):  ", e.getMessage());
            throw new FriendException("Error adding friend", e);
        }
    }

    /**
     * @param status Requester status, -1 rejected friends, 0 friend request, 1 friends
     */
    public void getFriendType(String userID, int status, RunnableFullProfileUI runnableFullProfileUI) throws FriendException {
        try {
            db.collection("users").document(userID).collection("friends")
                    .whereEqualTo("status", status).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        String uId = doc.get("requester").toString();
                        db.collection("users").document(uId).get()
                                .addOnCompleteListener(task1 -> {
                                    Controller_User.getInstance().getFullProfile(uId, runnableFullProfileUI);
                                });
                    }
                }
            });
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void getFriendType(String userID, int status, RunnableFullProfileUI runnableFullProfileUI):  ", e.getMessage());
            throw new FriendException("Error getting friend request", e);
        }
    }


    public void respondToFriendRequest(String friendID, int reqStatus, RunnableUI runnableUI) throws FriendException {
        //Set own friend to the status
        HashMap<String, Object> status = new HashMap<>();
        String selfID = mAuh.getCurrentUser().getUid();
        status.put("status", reqStatus);
        status.put("requester", friendID);

        try {
            //By accessing the database, merge to not overwrite extra data( if there is any)
            db.collection("users").document(selfID).collection("friends")
                    .document(friendID).set(status, SetOptions.merge()).addOnCompleteListener(task -> {
                //If changing own friend to status succeeded change friends status of you
                if (task.isSuccessful()) {
                    //Replace requester ID with self ID
                    //So not to add friend to himself
                    status.replace("requester", mAuh.getCurrentUser().getUid());
                    //Merge to not overwrite extra data
                    db.collection("users").document(friendID).collection("friends")
                            .document(selfID).set(status, SetOptions.merge());
                    try {
                        //Run methods if everything succeeds
                        runnableUI.run();
                    } catch (IDatabase.DatabaseException e) {
                        Sentry.addBreadcrumb("Called void respondToFriendRequest(String friendID, int reqStatus, RunnableUI runnableUI)->runnableUI.run(): ", e.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void respondToFriendRequest(String friendID, int reqStatus, RunnableUI runnableUI)");
            throw new FriendException("Error getting friend request", e);
        }
    }

    public void getFriends(RunnableProfilesUI runnableUI) throws IDatabase.DatabaseException {
        String id = mAuh.getCurrentUser().getUid();
        try {
            db.collection("users").document(id).collection("friends")
                    .whereEqualTo("status", 1).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    IProfile[] friends = new Profile[task.getResult().size()];
                    List<Profile> friendz = task.getResult().toObjects(Profile.class);
                    runnableUI.run(friendz.toArray(friends));
                }
            });

        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void getFriends(RunnableProfilesUI runnableUI)");
            throw new FriendException("Error getting friends", e);
        }
    }


}
