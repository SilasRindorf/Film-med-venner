package com.example.film_med_venner;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
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
            IProfile prof = new Profile("Profile " + i, "id" + i, i,i,i,i);
            profiles[i] = prof;
        }
        return profiles;
    }
    public ArrayList<IReview> generateReviews(int amount){
        ArrayList<IReview> rat = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            IReview rate  = new Review(i,"username" + i, "" + i, "review" + i);
            rat.add( rate);
        }
        return rat;
    }

    public ArrayList<IHomeFeedItems> generateHomeFeedItems(int amount){
        IMovie movie = new Movie("Bubbis Adult Movie", "2020", "Not porn", "Gruppe C6", "Bubbi");

        //IWatchItem watchlistItem = new WatchItem("Tronald Dump", 1);

        ArrayList<IHomeFeedItems> feedList = new ArrayList<IHomeFeedItems>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                IReview rating = new Review(j+1, "Kurger Bing", "Bubbi part " + i,"Some review" +i+j);
                feedList.add(rating);
            }
        }
        /*feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);
        feedList.add(watchlistItem);*/
        return feedList;
    }
}
