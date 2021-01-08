package com.example.film_med_venner.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Auth;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IDatabase;


public class MainActivity extends AppCompatActivity {
    private Controller_Auth auth = Controller_Auth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);

    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    public void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("User",MODE_PRIVATE);
        try {
            Database.getInstance().createUser("snoble","IWeak");
        } catch (IDatabase.DatabaseException e) {
            e.printStackTrace();
        }
        // Check if user is signed in (non-null) and update UI accordingly.
        //updateUI(currentUser);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}