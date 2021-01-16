package com.example.film_med_venner.controllers;

import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IController.IController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.facebook.AccessToken;



public class Controller_User implements IController {
    private static Controller_User instance;


    private Controller_User() {

    }

    public static Controller_User getInstance() {
        if (instance == null) {
            instance = new Controller_User();
        }
        return instance;
    }

    public void logIn(String email, String password, RunnableUI runnableUI) throws IDatabase.DatabaseException {
        Database.getInstance().logIn(email, password, runnableUI);
    }

    public boolean isLoggedIn() {
        return Database.getInstance().getCurrentUser() != null;
    }


    public void createUser(String email, String password, String name) {
        try {
            Database.getInstance().createUser(email, password, name, new RunnableErrorUI() {
                @Override
                public void run() {

                }

                @Override
                public void handleError(IDatabase.DatabaseException e) {

                }
            });
        } catch (Exception ignored) {
        }

    }

    public void logInWithFaceBook(String name,AccessToken token, RunnableUI runnableUI) throws IDatabase.DatabaseException  {
        Database.getInstance().loginWithFacebookUser(token,runnableUI);
    }
}
