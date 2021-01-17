package com.example.film_med_venner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.R;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableFullProfileUI;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.ui.profileActivities.FriendActivity;
import com.example.film_med_venner.ui.profileActivities.ReviewActivity;
import com.example.film_med_venner.ui.profileActivities.ToWatchlistActivity;
import com.example.film_med_venner.ui.profileActivities.WatchedlistActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout l_layout_review;
    LinearLayout l_layout_rating;
    LinearLayout l_layout_to_watchlist;
    LinearLayout l_layout_watchedlist;
    LinearLayout l_layout_friends;
    ImageView imageView_settings;
    ShapeableImageView profile_picture;
    TextView profileName, genrePref, friends, rated, watchList, watched;
    Profile profile;

    private Executor bgThread = Executors.newSingleThreadExecutor();
    private Handler uiThread = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        //TODO Vi skal lave det her på den smarte måde som Silas har vist og Sejr har glemt.
        l_layout_review = findViewById(R.id.linearLayout_review);
        l_layout_review.setOnClickListener(this);
        l_layout_rating = findViewById(R.id.linearLayout_rating);
        l_layout_rating.setOnClickListener(this);
        l_layout_to_watchlist = findViewById(R.id.linearLayout_to_watchlist);
        l_layout_to_watchlist.setOnClickListener(this);
        l_layout_watchedlist = findViewById(R.id.linearLayout_watchedlist);
        l_layout_watchedlist.setOnClickListener(this);
        l_layout_friends = findViewById(R.id.linearLayout_friends);
        l_layout_friends.setOnClickListener(this);
        imageView_settings = findViewById(R.id.imageView_settings);
        imageView_settings.setOnClickListener(this);
        profile_picture = findViewById(R.id.imageView_profile);

        profileName = findViewById(R.id.text_profileName);
        genrePref = findViewById(R.id.profileGenrePref);
        friends = findViewById(R.id.textView_friends_description);
        rated = findViewById(R.id.textView_rated_description);
        watchList = findViewById(R.id.textView_want_to_watch_description);
        watched = findViewById(R.id.textView_watchedlist_description);

        //TODO userID skal også kunne være en af dine venners

        /* if (bundle(userID) != null)
                userID = bundle(userID);
           else
                userID = Database.getInstance().getCurrentUser().getID();

         */


        // Virker åbenbart ikke :/
        bgThread.execute(() -> {
            Database.getInstance().getCurrentUser(RunnableFullProfileUI -> {
                String url = RunnableFullProfileUI.getPictureURL();
                Bitmap picture = downloadPP(url);
                uiThread.post(() -> {
                    System.out.println("ImageURL: " + url);
                    //Picasso.get().
                    //TODO Set profile picture in profile
                    profile_picture.setImageBitmap(picture);
                });
            });
            String userID = Database.getInstance().getCurrentUser().getID();
            try {
                Database.getInstance().getProfile(userID, profile1 -> {
                    profile = (Profile) profile1;
                    uiThread.post(() -> {
                        if (profile != null){
                            setupProfileInfo();
                        } else {
                            return;
                        }
                    });
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });

        // Det som den anden skal gøre (virker kun med currentUser)

        //profile = (Profile) Database.getInstance().getCurrentUser();
        //setupProfileInfo();


    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view == l_layout_rating){
            setContentView(R.layout.activity_rating);
            Intent intent = new Intent(this, ReviewActivity.class);
            startActivity(intent);
        }
        else if (view == l_layout_to_watchlist){
            setContentView(R.layout.activity_to_watchlist);
            Intent intent = new Intent(this, ToWatchlistActivity.class);
            startActivity(intent);
        }
        else if (view == l_layout_watchedlist){
            setContentView(R.layout.activity_watchedlist);
            Intent intent = new Intent(this, WatchedlistActivity.class);
            startActivity(intent);
        }
        else if (view == l_layout_friends){
            setContentView(R.layout.activity_friend);
            Intent intent = new Intent(this, FriendActivity.class);
            startActivity(intent);
        }
        else if (view == imageView_settings){
            setContentView(R.layout.settings_main);
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
    }

    private void setupProfileInfo() {
        profileName.setText(profile.getName());
        //genrePref.setText(profile.getMvgPrefs().toString());

        if (profile.getFriendIDs().length == 0) {
            friends.setText("You do not have any friends yet");
        } else if (profile.getFriendIDs().length == 1) {
            friends.setText("You have " + profile.getFriendIDs().length + " friend");
        } else {
            friends.setText("You have " + profile.getFriendIDs().length + " friends");
        }

        if (profile.getReviewedMovies().length == 0) {
            rated.setText("You have not rated any movies yet");
        } else if (profile.getReviewedMovies().length == 1) {
            rated.setText("You have rated " + profile.getReviewedMovies().length + " movie");
        } else {
            rated.setText("You have rated " + profile.getReviewedMovies().length + " movies");
        }

        if (profile.getMoviesOnToWatchList().length == 0) {
            watchList.setText("You have 0 movies on your watch list");
        } else if (profile.getMoviesOnToWatchList().length == 1) {
            watchList.setText("You have " + profile.getMoviesOnToWatchList().length + " movie on your watch list");
        } else {
            watchList.setText("You have " + profile.getMoviesOnToWatchList().length + " movies on your watch list");
        }

        if (profile.getMoviesOnWatchedList().length == 0) {
            watched.setText("You have 0 movies on your watch list");
        } else if (profile.getMoviesOnWatchedList().length == 1) {
            watched.setText("You have watched" + profile.getMoviesOnWatchedList().length + " movie");
        } else {
            watched.setText("You have watched " + profile.getMoviesOnWatchedList().length + " movies");
        }
    }

    /**
     * Credits to https://www.tutorialspoint.com/how-to-download-image-from-url-in-android
     * @param URL
     * @return
     */
    private Bitmap downloadPP(String... URL){
        String imageURL = URL[0];
        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageURL).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}