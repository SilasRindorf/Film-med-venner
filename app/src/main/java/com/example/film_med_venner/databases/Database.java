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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


//TODO should be handled in thread
public class Database implements IDatabase {

    private FirebaseFirestore db;
    private static Database instance;
    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }
    private Database(){
        db =  FirebaseFirestore.getInstance();
        //addUser("Bob Mclaren");
    }
    public boolean  addUser(String name, int userID){
        HashMap<Integer, Object> user = new HashMap();
        user.put(userID,name);
        db.collection("users").add(user)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "User added with ID: " + documentReference.getId());
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
        System.out.println("Users: " + db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        System.out.println("User: " + doc.getData());
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
}
