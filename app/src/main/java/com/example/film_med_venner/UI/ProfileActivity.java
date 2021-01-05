package com.example.film_med_venner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.film_med_venner.R;
import com.example.film_med_venner.UI.fragments.Nav_bar_frag;
import com.example.film_med_venner.UI.profileActivities.FriendActivity;
import com.example.film_med_venner.UI.profileActivities.RatingActivity;
import com.example.film_med_venner.UI.profileActivities.ReviewActivity;
import com.example.film_med_venner.UI.profileActivities.ToWatchlistActivity;
import com.example.film_med_venner.UI.profileActivities.WatchedlistActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout l_layout_review;
    LinearLayout l_layout_rating;
    LinearLayout l_layout_to_watchlist;
    LinearLayout l_layout_watchedlist;
    LinearLayout l_layout_friends;
    ImageView imageView_settings;

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
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view == l_layout_review){
            setContentView(R.layout.activity_review);
            Intent intent = new Intent(this, ReviewActivity.class);
            startActivity(intent);
        }
        else if (view == l_layout_rating){
            setContentView(R.layout.activity_rating);
            Intent intent = new Intent(this, RatingActivity.class);
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
}
