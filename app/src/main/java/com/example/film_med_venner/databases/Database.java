package com.example.film_med_venner.databases;

import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.google.firebase.firestore.FirebaseFirestore;



import java.util.ArrayList;
import java.util.HashMap;


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
        //FirebaseApp.initializeApp()
        db =  FirebaseFirestore.getInstance();
        //addUser("Bob Mclaren");
        System.out.println("Users: " + db.collection("users").get().toString());
    }
    public boolean  addUser(String name){
        HashMap<String, Object> user = new HashMap();
        user.put("name",name);
        db.collection("users").add(user);
        return true;
    }
    @Override
    public IProfile getProfile(int id) {
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
