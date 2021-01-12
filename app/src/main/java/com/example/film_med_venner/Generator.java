package com.example.film_med_venner;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
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
            IRating rate  = new Rating(i,"ursnam" + i, i, i,"review");
            rat.add( rate);
        }
        return rat;
    }

    public ArrayList<IHomeFeedItems> generateHomeFeedItems(int amount){
        IMovie movie = new Movie("Bee Movie", "info", new ArrayList<String>(), new String[3], "poster");

        //IWatchItem watchlistItem = new WatchItem("Tronald Dump", 1);

        ArrayList<IHomeFeedItems> feedList = new ArrayList<IHomeFeedItems>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                IRating rating = new Rating(j+1, "Kurger Bing", i,i+j,"Some review" + (10*i+j));
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
