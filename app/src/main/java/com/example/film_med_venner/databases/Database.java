package com.example.film_med_venner.databases;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;


//TODO should be handled in thread
public class Database implements IDatabase {
    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();
        //addUser("Bob Mclaren");
    }

    public boolean  addUser(String name, int userID){
        HashMap<Integer, Object> user = new HashMap();
        user.put(userID,name);
        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    public boolean isLoggedIn(){
        return mAuth.getCurrentUser() != null;
    }

    public void createUser(String email, String password) throws DatabaseException{
        final String[] msg = new String[1];
        final Exception[] ex = new Exception[1];
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task  -> {
            if(task.isSuccessful()){
                Log.d(TAG,"Create user with email: Success ");
            }
            else {
                Log.d(TAG,"Create user with email: Failed ");
                try {
                    throw task.getException();
                }
                catch(FirebaseAuthWeakPasswordException e) {
                    msg[0] = "Weak Password";
                    ex[0] = e;

                } catch(FirebaseAuthInvalidCredentialsException e) {
                    msg[0] = "Invalid Credentials";
                    ex[0] = e;
                } catch(FirebaseAuthUserCollisionException e) {
                    msg[0] = "Another user exists already";
                    ex[0] = e;
                } catch(Exception e) {
                    Log.e(TAG, e.getMessage());
                }

            }

        }
        );
        System.out.println(msg[0]);
        if (msg[0] != null){
            throw new DatabaseException(msg[0],ex[0]);
        }

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
