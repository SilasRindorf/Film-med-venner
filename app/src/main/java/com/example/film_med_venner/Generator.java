package com.example.film_med_venner;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.IWatchItem;

import java.util.ArrayList;

public class Generator {

    public IMovie[] generateMovies(int amount){
        IMovie[] movies = new Movie[amount];
        for (int i = 0; i < amount; i++) {
            Movie curMov  = new Movie("Movie " + i,
                    "",
                    null,
                    null,
                    "posterPath"
                    );
            movies[i] = curMov;
        }
        return movies;
    }
    public IProfile[] generateProfiles(int amount){
        IProfile[] profiles = new Profile[amount];
        for (int i = 0; i < amount; i++) {
            IProfile prof  = new Profile("Profile " + i, i, i, i, i, i, i);
            profiles[i] = prof;
        }
        return profiles;
    }
    public ArrayList<IRating> generateRatings(int amount){
        ArrayList<IRating> rat = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            IRating rate  = new Rating(i,"ursnam" + i, i, i);
            rat.add( rate);
        }
        return rat;
    }

    public ArrayList<IReview> generateReview(int amount){
        ArrayList<IReview> reviews = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            IReview rate  = new Review(i,"ursnam" + i, i, i,"gret movei");
            reviews.add( rate);
        }
        return reviews;
    }

    public ArrayList<IHomeFeedItems> generateHomeFeedItems(int amount){
        IMovie movie = new Movie("Bee Movie", "info", "new ArrayList<String>()", "new String[3]", "poster");
        IRating rating = new Rating(1000, "Kurger Bing", 2,65);
        IReview review = new Review(2, "Kurger Bing", 1,5,"Very bee, much buzz");
        IWatchItem watchlistItem = new WatchItem("Tronald Dump", 1);

        ArrayList<IHomeFeedItems> feedList = new ArrayList<IHomeFeedItems>();
        feedList.add(rating);
        feedList.add(review);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        return feedList;
    }
}
