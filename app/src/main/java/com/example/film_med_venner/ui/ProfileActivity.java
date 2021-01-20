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
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
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
    private TextView profileName, genrePref, friends, reviewed, watchList, watchedList;
    private FullProfileDTO profile;
    private String url, userID;
    private int friendCount, friendRequestCount;
    private boolean isUser = true;

    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);

        findViews();

        Intent intent = getIntent();

        if (intent.getStringExtra("userID") == null || intent.getStringExtra("userID").equals(Controller_User.getInstance().getCurrentUser().getID())) {
            userID = Controller_User.getInstance().getCurrentUser().getID();
        } else {
            userID = intent.getStringExtra("userID");
            imageView_settings.setVisibility(View.INVISIBLE);
            isUser = false;
        }

        bgThread.execute(() -> {
            runLoadScreen(true);
            friendCount = 0;
            friendRequestCount = 0;
            try {
                Controller_Friends.getInstance().getFriendType(userID,1, friends -> {
                    friendCount++;
                    uiThread.post(() -> setupFriendsInfo());
                });
                if (isUser) {
                    Controller_Friends.getInstance().getFriendType(userID, 0, friends -> {
                        friendRequestCount++;
                        uiThread.post(() -> setupFriendsInfo());
                    });
                }
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }

            Controller_User.getInstance().getFullProfile(userID, RunnableFullProfileUI -> {
                profile = RunnableFullProfileUI;
                url = profile.getPictureURL();
                uiThread.post(() -> {
                    System.out.println("hvem kommer først?");
                    setupProfileInfo();
                    if (url != null) {
                        Picasso.get().load(url).into(profile_picture);
                    }
                    runLoadScreen(false);
                });
            });
        });
    }

    private void runLoadScreen(boolean keep) {
        Intent ld = new Intent(ProfileActivity.this, LoadingScreen.class);
        ld.putExtra("finished", keep);
        ld.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ld.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(ld);
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
            intent.putExtra("friendRequests", friendRequestCount);
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

    private void setupFriendsInfo() {
        String user;
        String full;

        if (profile.getID().equals(Controller_User.getInstance().getCurrentUser().getID())) {
            user = "You have ";
        } else {
            user = profile.getName() + " has ";
        }

        switch (friendCount) {
            case 0: full = user + "not gotten any friends yet"; break;
            case 1: full = user + friendCount + " friend"; break;
            default:full = user + friendCount + " friends";
        }

        switch (friendRequestCount) {
            case 0: break;
            case 1: full += " and " + friendRequestCount + " friend request"; break;
            default:full += " and " + friendRequestCount + " friend requests";
        }
        friends.setText(full);

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

        switch (profile.getReviews().size()) {
            case 0: full = user + "not reviewed any movies yet"; break;
            case 1: full = user + "reviewed " + profile.getReviews().size() + " movie"; break;
            default:full = user + "reviewed " + profile.getReviews().size() + " movies";
        }
        reviewed.setText(full);

        switch (profile.getToWatchList().size()) {
            case 0: full = user + "no movies on " + extra + " watch list yet"; break;
            case 1: full = user + profile.getToWatchList().size() + " movie on " + extra + " watch list"; break;
            default:full = user + profile.getToWatchList().size() + " movies on " + extra + " watch list";
        }
        watchList.setText(full);


        switch (profile.getWatchedList().size()) {
            case 0: full = user + "not watched any movies yet"; break;
            case 1: full = user + "watched " + profile.getWatchedList().size() + " movie"; break;
            default:full = user + "watched " + profile.getWatchedList().size() + " movies";
        }
        watchedList.setText(full);
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
        reviewed = findViewById(R.id.textView_rated_description);
        watchList = findViewById(R.id.textView_want_to_watch_description);
        watchedList = findViewById(R.id.textView_watchedlist_description);
    }
}