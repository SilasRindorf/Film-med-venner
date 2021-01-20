package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DTO.WatchItemDTO;
import com.example.film_med_venner.interfaces.IController.IController_HomeFeed;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IWatchItem;
import com.example.film_med_venner.interfaces.runnable.RunnableWatchListUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Document;

import java.util.List;


public class Controller_HomeFeed implements IController_HomeFeed {
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuh;
    private static Controller_HomeFeed instance;
    public static Controller_HomeFeed getInstance(){
        if (instance == null){
            instance = new Controller_HomeFeed();
        }
        return instance;
    }

    private Controller_HomeFeed(){
        db = FirebaseFirestore.getInstance();
        mAuh = FirebaseAuth.getInstance();
    }

    //----------------------------------WATCHLIST----------------------------------
    public void addToWatchListItem(IWatchItem watchItem) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(mAuh.getCurrentUser().getUid())
                    .collection("to_watch_list").document(watchItem.getMovieIDStr())
                    .set(new WatchItemDTO(watchItem), SetOptions.merge());
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error creating watch item", e);
        }
    }

    public void removeToWatchListItem(String movieIDStr) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(mAuh.getCurrentUser().getUid())
                    .collection("to_watch_list").document(movieIDStr)
                    .delete()
                    .addOnCompleteListener(task -> {
                    });
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error creating watch item", e);
        }
    }

    public void getToWatchList(String userID, RunnableWatchListUI runnableWatchListUI) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(userID)
                    .collection("to_watch_list")
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    IWatchItem[] toWatchList = new WatchItemDTO[task.getResult().size()];
                    List<WatchItemDTO> watchItems = task.getResult().toObjects(WatchItemDTO.class);
                    runnableWatchListUI.run(watchItems.toArray(toWatchList));
                }
            });
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error creating watch item", e);
        }
    }

    public void addWatchedListItem(IWatchItem watchItem) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(mAuh.getCurrentUser().getUid())
                    .collection("watched_list")
                    .add(new WatchItemDTO(watchItem));
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error creating watch item", e);
        }
    }

    public void getWatchedList(String userID, RunnableWatchListUI runnableWatchListUI) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(userID)
                    .collection("watched_list")
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    IWatchItem[] toWatchList = new WatchItemDTO[task.getResult().size()];
                    List<WatchItemDTO> watchItems = task.getResult().toObjects(WatchItemDTO.class);
                    runnableWatchListUI.run(watchItems.toArray(toWatchList));
                }
            });
        } catch (Exception e) {
            throw new IDatabase.DatabaseException("Error creating watch item", e);
        }
    }

}
