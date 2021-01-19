package com.example.film_med_venner.ui.profileActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Review;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.ui.adapters.ReviewAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IReview;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ReviewActivity extends AppCompatActivity {
    GridView gridView;
    private ReviewAdapter reviewAdapter;
    private Context ctx;
    private List<IReview> items = new ArrayList<>();
    private String userID;
    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ctx = this;

        Intent intent = getIntent();

        if (intent.getStringExtra("userID") == null || intent.getStringExtra("userID").equals(Controller_User.getInstance().getCurrentUser().getID())) {
            userID = Controller_User.getInstance().getCurrentUser().getID();
        } else {
            userID = intent.getStringExtra("userID");
        }

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        gridView = findViewById(R.id.gridView);

        bgThread.execute(() -> {
            try {
                Controller_Review.getInstance().getReviews(userID, reviews -> {
                    items = Arrays.asList(reviews);
                    uiThread.post(() -> {
                        reviewAdapter = new ReviewAdapter(ctx, items);
                        gridView.setAdapter(reviewAdapter);
                        gridView.setVisibility(View.VISIBLE);
                    });
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }


}