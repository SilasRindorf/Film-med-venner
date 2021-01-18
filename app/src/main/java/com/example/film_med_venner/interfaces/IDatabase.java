package com.example.film_med_venner.interfaces;

import com.example.film_med_venner.databases.DatabaseNonPers;

import java.util.ArrayList;

public interface IDatabase {

    static IDatabase getInstance() {
        return DatabaseNonPers.getInstance();
    }

    /**
     * error ID's here
     * -1 = unknown
     * 0 = quit
     * 1 =
     * 100+  = users
     * 101 = Weak Password
     * 102 = Invalid Credentials
     * 103 = User Collision
     * 200+ = movies
     * 300+ = ratings
     */
    class DatabaseException extends Exception {

        private int errorID = -1;

        public DatabaseException(String message) {
            super(message);
        }

        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }

        public DatabaseException(String message, Throwable cause, int errorID) {
            super(message, cause);
            this.errorID = errorID;
        }

        public int getErrorID() {
            return errorID;
        }
    }
}
