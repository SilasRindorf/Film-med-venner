package com.example.film_med_venner.interfaces;

import com.example.film_med_venner.databases.DatabaseNonPers;

import java.util.ArrayList;

public interface IDatabase {
    IProfile getProfile(String id);

    IMovie[] getMoviesWithGenre(String Genre);
    IMovie[] getMovies();
    IProfile[] getProfiles();
    ArrayList<IHomeFeedItems> getHomeFeed();
    IRating[] getRating();

    void sendFriendRequest(String id) throws DatabaseException;

    static IDatabase getInstance() {
        return DatabaseNonPers.getInstance();
    }

    class DatabaseException extends Exception{
        //Put error ID's here
        // -1 = unknown
        // 0 = quit
        // 1 =
        // 100+  = users
        // 200+ = movies
        // 300+ = ratings
        private int errorID = -1;
        public DatabaseException(String message) { super(message); }
        public DatabaseException(String message, Throwable cause) { super(message, cause); }
        public DatabaseException(String message, Throwable cause, int errorID) { super(message, cause);
        this.errorID = errorID;
        }
        public int getErrorID() {
            return errorID;
        }
    }
}
