package com.example.film_med_venner.databases;

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.Generator;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IReview;

import java.util.ArrayList;


public class DatabaseNonPers implements IDatabase {
    //private HashMap<Enums.Genre[], IMovie> movies;
    private IProfile[] profiles;
    private IMovie[] movies;
    private ArrayList<IHomeFeedItems> homeFeedItems;
    private ArrayList<IReview> ratings;
    private static IDatabase instance;

    public static IDatabase getInstance() {
        if (instance == null) {
            instance = new DatabaseNonPers();
        }
        return instance;
    }

    private DatabaseNonPers() {
        Generator gen = new Generator();
        movies = gen.generateMovies(10);
        profiles = gen.generateProfiles(10);
        homeFeedItems = gen.generateHomeFeedItems(10);
        ratings = gen.generateReviews(10);
    }

    public IProfile getProfile(int id) {
        //Temporary return ID is tied to a profile not pos in array
        return profiles[id];
    }

    public IProfile[] getFriends(int ID) {
        /*IProfile[] friends = new Profile[profiles[ID].getAmountOfFriends()];
        String[] friendIDs = profiles[ID].getFriendIDs();
        for (int i = 0; i < friendIDs.length; i++) {
            friends[i] = profiles[friendIDs[i]];
        }*/
        //return friends;
        return null;
    }

    public IReview[] getReview() {
        IReview[] rat = new Review[this.ratings.size()];
        return ratings.toArray(rat);
    }



}
