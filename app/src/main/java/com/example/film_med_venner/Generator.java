package com.example.film_med_venner;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchlistItem;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.IWatchlistItem;

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
    public IRating[] generateRatings(int amount){
        IRating[] profiles = new Rating[amount];
        for (int i = 0; i < amount; i++) {
            IRating rate  = new Rating(i,"ursnam" + i, i, i);
            profiles[i] = rate;
        }
        return profiles;
    }

    public IRating[] generateReviewItems(int amount){
        IRating[] reviews = new Review[amount];
        for (int i = 0; i < amount; i++) {
            IRating rate  = new Review(i,"ursnam" + i, i, i,"gret movei");
            reviews[i] = rate;
        }
        return reviews;
    }

    public ArrayList<IHomeFeedItems> generateHomeFeedItems(int amount){
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<String>(), new String[3], "poster");
        IRating rating = new Rating(1000, "Kurger Bing", amount,65);
        IReview review = new Review(2, "Kurger Bing", 1,5,"Very bee, much buzz");
        IWatchlistItem watchlistItem = new WatchlistItem("Tronald Dump", 1);

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
