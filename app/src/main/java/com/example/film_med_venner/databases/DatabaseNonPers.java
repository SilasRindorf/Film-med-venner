package com.example.film_med_venner.databases;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.Generator;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;

import java.util.ArrayList;


public class DatabaseNonPers implements IDatabase {
    //private HashMap<Enums.Genre[], IMovie> movies;
    private IProfile[] profiles;
    private IMovie[] movies;
    private ArrayList<IHomeFeedItems> homeFeedItems;
    private ArrayList<IReview> reviews;
    private ArrayList<IRating> ratings;
    private static IDatabase instance;

    public static IDatabase getInstance() {
        if (instance == null){
            instance = new DatabaseNonPers();
        }
        return instance;
    }

    private DatabaseNonPers(){
        Generator gen = new Generator();
        movies = gen.generateMovies(10);
        profiles = gen.generateProfiles(10);
        homeFeedItems = gen.generateHomeFeedItems(10);
    }

    @Override
    public IProfile getProfile(int id) {
        //Temporary return ID is tied to a profile not pos in array
        return profiles[id];
    }

    @Override
    public IMovie[] getMoviesWithGenre(String Genre) {
        return null;
    }


    @Override
    public IMovie[] getMovies() {   
        return movies;
    }

    @Override
    public IProfile[] getProfiles() {
        return profiles;
    }

    @Override
    public ArrayList<IHomeFeedItems> getHomeFeed() {
        return homeFeedItems;
    }

    @Override
    public IReview[] getReviews() {
        IReview[] rev  = new Review[reviews.size()];
        return reviews.toArray(rev);
    }

    public IProfile[] getFriends(int ID) {
        IProfile[] friends = new Profile[profiles[ID].getAmountOfFriends()];
        int[] friendIDs = profiles[ID].getFriendIDs();
        for (int i = 0; i < friendIDs.length; i++) {
            friends[i] = profiles[friendIDs[i]];
        }
        return friends;
    }

    public IRating[] getRating() {
        IRating[] rat = new Rating[this.ratings.size()];
        return ratings.toArray(rat);
    }

}
