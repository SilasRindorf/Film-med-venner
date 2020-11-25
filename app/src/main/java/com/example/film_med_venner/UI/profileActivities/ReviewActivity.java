package com.example.film_med_venner.UI.profileActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchlistItem;
import com.example.film_med_venner.R;
import com.example.film_med_venner.UI.Adapters.ReviewAdapter;
import com.example.film_med_venner.UI.fragments.Nav_bar_frag;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.IMovie;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.IWatchlistItem;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    GridView gridView;
    private ReviewAdapter ReviewAdapter;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        gridView = findViewById(R.id.gridView);

    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupHomeFeed(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupHomeFeed(false);
    }

    void setupHomeFeed(boolean run) {
        AsyncTask asyncTask = new AsyncTask() {
            List<IReview> items = new ArrayList<>();
            String errorMsg = null;

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Object doInBackground(Object... arg0) {
                try {

                    // Dummy data
                    IMovie movie = new Movie("Bee Movie", "info", new ArrayList<String>(), new String[3], "poster");
                    IReview review = new Review("Very bee, much buzz", "Kurger Bing", movie);

                    ArrayList<IReview> feedList = new ArrayList<IReview>();
                    feedList.add(review);
                    feedList.add(review);
                    feedList.add(review);
                    feedList.add(review);




                    items =  feedList;
                    return null;
                } catch (Exception e) {
                    //    errorMsg = e.getMessage();
                    e.printStackTrace();
                    return e;
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected void onPostExecute(Object titler) {
                ReviewAdapter = new ReviewAdapter(ctx, items);
                gridView.setAdapter(ReviewAdapter);
                gridView.setVisibility(View.VISIBLE);
            }

        };

        if (run) {
            asyncTask.execute();
        } else {
            asyncTask.cancel(true);
        }
    }
}