package com.example.film_med_venner.controllers;

import com.example.film_med_venner.DTO.WatchItemDTO;
import com.example.film_med_venner.interfaces.IController.IController_HomeFeed;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IWatchItem;
import com.example.film_med_venner.interfaces.runnable.RunnableWatchListUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.List;

import io.sentry.Sentry;


public class Controller_HomeFeed implements IController_HomeFeed {
    private static Controller_HomeFeed instance;
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuh;

    private Controller_HomeFeed() {
        db = FirebaseFirestore.getInstance();
        mAuh = FirebaseAuth.getInstance();
    }

    public static Controller_HomeFeed getInstance() {
        if (instance == null) {
            instance = new Controller_HomeFeed();
        }
        return instance;
    }

    //----------------------------------WATCHLIST----------------------------------
    public void addToWatchListItem(IWatchItem watchItem) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(mAuh.getCurrentUser().getUid())
                    .collection("to_watch_list").document(watchItem.getMovieIDStr())
                    .set(new WatchItemDTO(watchItem), SetOptions.merge());
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void addToWatchListItem(IWatchItem watchItem):  ", e.getMessage());
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
            Sentry.addBreadcrumb("Called void removeToWatchListItem(String movieIDStr):  ", e.getMessage());
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
            Sentry.addBreadcrumb("Called void getToWatchList(String userID, RunnableWatchListUI runnableWatchListUI):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error creating watch item", e);
        }
    }

    public void addWatchedListItem(IWatchItem watchItem) throws IDatabase.DatabaseException {
        try {
            db.collection("users").document(mAuh.getCurrentUser().getUid())
                    .collection("watched_list")
                    .add(new WatchItemDTO(watchItem));
        } catch (Exception e) {
            Sentry.addBreadcrumb("Called void addWatchedListItem(IWatchItem watchItem):  ", e.getMessage());
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
            Sentry.addBreadcrumb("Called void getWatchedList(String userID, RunnableWatchListUI runnableWatchListUI):  ", e.getMessage());
            throw new IDatabase.DatabaseException("Error creating watch item", e);
        }
    }

}
