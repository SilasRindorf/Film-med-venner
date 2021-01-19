package com.example.film_med_venner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.ui.profileActivities.FriendActivity;
import com.example.film_med_venner.ui.profileActivities.ReviewActivity;
import com.example.film_med_venner.ui.profileActivities.SettingsActivity;
import com.example.film_med_venner.ui.profileActivities.SettingsFacebookUserActivity;
import com.example.film_med_venner.ui.profileActivities.ToWatchlistActivity;
import com.example.film_med_venner.ui.profileActivities.WatchedlistActivity;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout l_layout_rating;
    private LinearLayout l_layout_to_watchlist;
    private LinearLayout l_layout_watchedlist;
    private LinearLayout l_layout_friends;
    private ImageView imageView_settings;
    private ImageView profile_picture;
    private TextView profileName, genrePref, friends, rated, watchList, watchedList;
    private FullProfileDTO profile;
    private String url, userID;

    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);

        findViews();

        //TODO userID skal også kunne være en af dine venners

        Intent intent = getIntent();

        if (intent.getStringExtra("userID") == null || intent.getStringExtra("userID").equals(Controller_User.getInstance().getCurrentUser().getID())) {
            userID = Controller_User.getInstance().getCurrentUser().getID();
        } else {
            userID = intent.getStringExtra("userID");
            imageView_settings.setVisibility(View.INVISIBLE);
        }

        bgThread.execute(() -> {
            Controller_User.getInstance().getFullProfile(userID, RunnableFullProfileUI -> {
                profile = RunnableFullProfileUI;
                url = profile.getPictureURL();
                uiThread.post(() -> {
                    setupProfileInfo();
                    if (url != null) {
                        Picasso.get().load(url).into(profile_picture);
                    }
                });
            });
        });
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {

        if (view == l_layout_rating) {
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        } else if (view == l_layout_to_watchlist) {
            Intent intent = new Intent(this, ToWatchlistActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        } else if (view == l_layout_watchedlist) {
            Intent intent = new Intent(this, WatchedlistActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        } else if (view == l_layout_friends) {
            Intent intent = new Intent(this, FriendActivity.class);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }

    }

    public void onClickSettings(View view) {
        if (!Controller_User.getInstance().isFacebookUserLoginValid()) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
        } else {
            Intent intent = new Intent(this, SettingsFacebookUserActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
        }
    }


    private void setupProfileInfo() {
        profileName.setText(profile.getName());
        genrePref.setText(profile.getmvGPrefs());
        String user;
        String extra;
        String full;

        if (profile.getID().equals(Controller_User.getInstance().getCurrentUser().getID())) {
            user = "You have ";
            extra = "your";
        } else {
            user = profile.getName() + " has ";
            extra = "their";
        }

        if (profile.getFriends().size() == 0) {
            full = user + "not gotten any friends yet";
            friends.setText(full);
        } else if (profile.getFriends().size() == 1) {
            full = user + profile.getFriends().size() + " friend";
            friends.setText(full);
        } else {
            full = user + profile.getFriends().size() + " friends";
            friends.setText(full);
        }

        if (profile.getReviews().size() == 0) {
            full = user + "not rated any movies yet";
            rated.setText(full);
        } else if (profile.getReviews().size() == 1) {
            full = user + "rated " + profile.getReviews().size() + " movie";
            rated.setText(full);
        } else {
            full = user + "rated " + profile.getReviews().size() + " movies";
            rated.setText(full);
        }

        if (profile.getToWatchList().size() == 0) {
            full = user + "no movies on " + extra + " watch list yet";
            watchList.setText(full);
        } else if (profile.getToWatchList().size() == 1) {
            full = user + profile.getToWatchList().size() + " movie on " + extra + "watch list";
            watchList.setText(full);
        } else {
            full = user + "rated " + profile.getToWatchList().size() + " movies on " + extra + "watch list";
            watchList.setText(full);
        }

        if (profile.getWatchedList().size() == 0) {
            full = user + "not watched any movies yet";
            watchedList.setText(full);
        } else if (profile.getWatchedList().size() == 1) {
            full = user + "watched " + profile.getWatchedList().size() + " movie";
            watchedList.setText(full);
        } else {
            full = user + "watched " + profile.getWatchedList().size() + " movies";
            watchedList.setText(full);
        }
    }

    private void findViews() {
        l_layout_rating = findViewById(R.id.linearLayout_rating);
        l_layout_rating.setOnClickListener(this);
        l_layout_to_watchlist = findViewById(R.id.linearLayout_to_watchlist);
        l_layout_to_watchlist.setOnClickListener(this);
        l_layout_watchedlist = findViewById(R.id.linearLayout_watchedlist);
        l_layout_watchedlist.setOnClickListener(this);
        l_layout_friends = findViewById(R.id.linearLayout_friends);
        l_layout_friends.setOnClickListener(this);
        imageView_settings = findViewById(R.id.imageView_settings);
        profile_picture = findViewById(R.id.imageView_profile);

        profileName = findViewById(R.id.text_profileName);
        genrePref = findViewById(R.id.profileGenrePref);
        friends = findViewById(R.id.textView_friends_description);
        rated = findViewById(R.id.textView_rated_description);
        watchList = findViewById(R.id.textView_want_to_watch_description);
        watchedList = findViewById(R.id.textView_watchedlist_description);
    }
}