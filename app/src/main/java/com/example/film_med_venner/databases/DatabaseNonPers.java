package com.example.film_med_venner.databases;

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
    private final IProfile[] profiles;
    private final IMovie[] movies;
    private final ArrayList<IHomeFeedItems> homeFeedItems;
    private final ArrayList<IReview> reviews;
    private final ArrayList<IRating> ratings;
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
        ratings = gen.generateRatings(10);
        reviews = gen.generateReview(10);
    }

    public IProfile getProfile(int id) {
        //Temporary return ID is tied to a profile not pos in array
        return profiles[id];
    }

    @Override
    public IProfile getProfile(String id) {
        return null;
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
        IReview[] rev = new Review[reviews.size()];
        return reviews.toArray(rev);
    }

    public IProfile[] getFriends(String ID) {
        return profiles;
    }

    public IRating[] getRating() {
        IRating[] rat = new Rating[this.ratings.size()];
        return ratings.toArray(rat);
    }

    @Override
    public void sendFriendRequest(String id) throws DatabaseException {
    }

}
