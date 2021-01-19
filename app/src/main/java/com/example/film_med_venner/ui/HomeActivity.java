package com.example.film_med_venner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Review;
import com.example.film_med_venner.ui.adapters.HomeAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    private HomeAdapter homeAdapter;
    private Context ctx;
    private View v;
    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();
    Controller_Review controller = Controller_Review.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);
        listView = findViewById(R.id.listView);
        //TODO Homefeed not working
        /*bgThread.execute(() -> {
            try {
                List<IReview> items = new ArrayList<>();
                controller.getReviews((RunnableReviewsUI) -> {
                    items = controller.
                    uiThread.post(() -> {
                        homeAdapter = new HomeAdapter(ctx, items);
                        listView.setAdapter(homeAdapter);
                        listView.setVisibility(View.VISIBLE);
                    });
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        setContentView(R.layout.feed_rated_item_description);
        Intent intent = new Intent(this, ReviewedItemActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    public void onClickPoster(View view) {


    }

    public void goToReview(View view){
        String clickedReviewText = getClickedReview(((TextView) view).getText().toString());
        int clickedReviewRating = getClickedRating(((TextView) view).getText().toString());
        String clickedReviewDescription = getClickedDescription(((TextView) view).getText().toString());

        setContentView(R.layout.feed_rated_item_description);
        Intent intent = new Intent(this, ReviewedItemActivity.class);

        System.out.println(clickedReviewText);
        intent.putExtra("reviewText",clickedReviewText);

        System.out.println(clickedReviewRating);
        intent.putExtra("starReview", clickedReviewRating);

        System.out.println(clickedReviewDescription);
        intent.putExtra("reviewDescription",clickedReviewDescription);

        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);

    }

    public String getClickedReview(String clickedText){
        //TODO NOT WORKING
        /*List<IHomeFeedItems> items = controller.getHomeFeedItems();
        for (IHomeFeedItems item : items){
            String expectedReviewText = ((Review) item).getReview();
            if (expectedReviewText.length() > 200){
                expectedReviewText = (expectedReviewText.substring(0,200) + "...");
            }
            if (expectedReviewText.equals(clickedText)){
                return ((Review) item).getReview();
            }
        }*/
        return "ERROR";
    }

    public int getClickedRating(String clickedText){
        //TODO NOT WORKING
        /*
        List<IHomeFeedItems> items = controller.getHomeFeedItems();
        for (IHomeFeedItems item : items){
            String expectedReviewText = ((Review) item).getReview();
            if (expectedReviewText.equals(clickedText)){
                return (((Review) item).getRating());
            }
        }*/
        return 0;
    }

    public String getClickedDescription(String clickedText){
        //TODO NOT WORKING
        /*
        List<IHomeFeedItems> items = controller.getHomeFeedItems();
        for (IHomeFeedItems item : items){
            String expectedReviewText = ((Review) item).getReview();
            if (expectedReviewText.equals(clickedText)){
                return (item.getUsername() + " has rated " + item.getMovieIDStr() + " with " +
                        ((Review) item).getRating() + " stars.");
            }
        }*/
        return "ERROR";
    }
}