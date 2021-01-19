package com.example.film_med_venner.controllers;


import android.util.Log;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.interfaces.runnable.RunnableFullProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableProfilesUI;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

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
     * @param friendID friends ID string
     * @throws IDatabase.DatabaseException
     */
    public void sendFriendRequest(String friendID) throws IDatabase.DatabaseException {
        HashMap<String, Object> user = new HashMap<>();
        String selfID = mAuh.getCurrentUser().getUid();
        user.put("requester", selfID);
        user.put("status", 0);
        try {
            db.collection("users").document(friendID).collection("friends").document(selfID)
                    .set(user, SetOptions.merge()).addOnSuccessListener(aVoid ->
                    Log.d(TAG, "Friend request send to ID: " + friendID))
                    .addOnFailureListener(e ->
                            Log.w(TAG, "Error sending friend request", e));
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error adding friend", e);
        }

    }

    /**
     * Send a request to the database to add a friend
     * @param email friends email string
     * @throws IDatabase.DatabaseException
     */
    public void sendFriendRequestByMail(String email, RunnableErrorUI runnableErrorUI)  {
        HashMap<String, Object> user = new HashMap<>();
        String selfID = mAuh.getCurrentUser().getUid();
        user.put("requester", selfID);
        user.put("status", 0);
        try {
            db.collection("users").whereEqualTo("email",email).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc :
                            task.getResult()) {
                        doc.getId();
                        db.collection("users").document(doc.getId()).collection("friends").document(selfID)
                                .set(user, SetOptions.merge()).addOnSuccessListener(aVoid ->
                                Log.d(TAG, "Friend request send to ID: " + doc.getId()))
                                .addOnFailureListener(e ->
                                        Log.w(TAG, "Error sending friend request", e));
                    }
                }
            });
        } catch (Exception e) {
            runnableErrorUI.handleError(new IDatabase.DatabaseException("Error adding friend", e));
        }

    }

    /**
     *
     * @param status Requester status, -1 rejected friends, 0 friend request, 1 friends
     * @param runnableFullProfileUI method to run on complete
     * @throws IDatabase.DatabaseException
     */
    public void getFriendRequest(String userID, int status, RunnableFullProfileUI runnableFullProfileUI) throws IDatabase.DatabaseException {
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
            throw new IDatabase.DatabaseException("Error getting friend request", e);
        }
    }


    public void respondToFriendRequest(String friendID, int reqStatus, RunnableUI runnableUI) throws IDatabase.DatabaseException {
        HashMap<String, Object> status = new HashMap<>();
        String selfID = mAuh.getCurrentUser().getUid();
        status.put("status", reqStatus);
        status.put("requester", friendID);

        try {
            db.collection("users").document(selfID).collection("friends")
                    .document(friendID).set(status, SetOptions.merge()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    status.replace("requester", mAuh.getCurrentUser().getUid());
                    db.collection("users").document(friendID).collection("friends")
                            .document(selfID).set(status, SetOptions.merge());
                    try {
                        runnableUI.run();
                    } catch (IDatabase.DatabaseException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error getting friend request", e);
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
            throw new IDatabase.DatabaseException("Error getting friends", e);
        }
    }


}
