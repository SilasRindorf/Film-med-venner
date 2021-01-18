package com.example.film_med_venner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    private LinearLayout l_layout_rating;
    private LinearLayout l_layout_to_watchlist;
    private LinearLayout l_layout_watchedlist;
    private LinearLayout l_layout_friends;
    private ImageView imageView_settings;
    private ImageView profile_picture;
    private TextView profileName, genrePref, friends, rated, watchList, watched;
    private FullProfileDTO profile;
    private String url, userID;

    private Executor bgThread = Executors.newSingleThreadExecutor();
    private Handler uiThread = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        findViews();

        //TODO userID skal også kunne være en af dine venners

        intent = getIntent();



        if (intent.getStringExtra("userID") != null)
            userID = intent.getStringExtra("userID");
        else
            userID = Controller_User.getInstance().getCurrentUser().getID();

        bgThread.execute(() -> {
            Controller_User.getInstance().getFullProfile(userID, RunnableFullProfileUI -> {
                profile = RunnableFullProfileUI;
                url = profile.getPictureURL();
                uiThread.post(() -> {
                    setupProfileInfo();
                    if (url != null){
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
            if (!Controller_User.getInstance().isFacebookUserLoginValid()){
                setContentView(R.layout.settings_main);
                Intent newIntent = new Intent(this, SettingsActivity.class);
                startActivity(newIntent);
            } else {
                setContentView(R.layout.settings_facebook_user);
                Intent newIntent = new Intent(this, SettingsFacebookUserActivity.class);
                startActivity(newIntent);
            }
        }
    }

    private void setupProfileInfo() {
        profileName.setText(profile.getName());
        //genrePref.setText(profile.getMvgPrefs().toString());
        String user;

        if (profile.getID().equals(Controller_User.getInstance().getCurrentUser().getID())) {
            user = "You have ";
        } else {
            user = profile.getName() + " has ";
        }

        if (profile.getFriends().size() == 0) {
            friends.setText(user + "not gotten any friends yet");
        } else if (profile.getFriends().size() == 1) {
            friends.setText(user + profile.getFriends().size() + " friend");
        } else {
            friends.setText(user + profile.getFriends().size() + " friends");
        }

        if (profile.getReviews().size() == 0) {
            rated.setText(user + "not rated any movies yet");
        } else if (profile.getReviews().size() == 1) {
            rated.setText(user + "rated " + profile.getReviews().size() + " movie");
        } else {
            rated.setText(user + "rated " + profile.getReviews().size() + " movies");
        }
    }
    private void findViews(){
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
    }
}