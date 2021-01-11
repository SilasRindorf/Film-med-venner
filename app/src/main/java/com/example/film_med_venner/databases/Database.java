package com.example.film_med_venner.databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        //addUser("Bob Mclaren");
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public boolean addUser(String name, String userID) {
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
        return true;
    }

    @Override
    public IProfile getProfile(int id) {
        Log.d(TAG,"Users: " + db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Log.d(TAG,"User: " + doc.getData());
                    }
                }
            }
        }));
        return null;
    }



    @Override
    public IMovie[] getMoviesWithGenre(String Genre) {
        return new IMovie[0];
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
