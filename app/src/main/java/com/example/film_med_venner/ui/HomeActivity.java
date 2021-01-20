package com.example.film_med_venner.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DTO.FriendDTO;
import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.DTO.ProfileDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.controllers.Controller_Review;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.runnable.RunnableFullProfileUI;
import com.example.film_med_venner.ui.adapters.HomeAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class HomeActivity extends AppCompatActivity {
    ListView listView;
    private HomeAdapter homeAdapter;
    private Context ctx;
    private TreeMap<Date, IReview> map = new TreeMap<>();
    private Review[] reviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);
        listView = findViewById(R.id.listView);


        TreeMap<Date, IReview> map = new TreeMap<>(Collections.reverseOrder());



        Controller_User.getInstance().getFullProfile(Controller_User.getInstance().getCurrentUser().getID(), fullProfileDTO -> {

            for (FriendDTO p : fullProfileDTO.getFriends()) {
                try {
                    Controller_Review.getInstance().getReviews(p.getRequester(), ratings -> {
                        for (IReview review : ratings) {
                            map.put(review.getCreationDate(), review);
                            arrangeReviews(map);
                            homeAdapter = new HomeAdapter(ctx, map);
                            listView.setAdapter(homeAdapter);
                            listView.setVisibility(View.VISIBLE);
                        }

                    });
                } catch (IDatabase.DatabaseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    public void onClickReview(View v) {
        setContentView(R.layout.feed_rated_item_description);
        Intent intent = new Intent(this, ReviewedItemActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
    }

    public void onClickPoster(View view) {
        int position = listView.getPositionForView(view);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("Id", reviews[position].getMovieIDStr());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void arrangeReviews(TreeMap<Date, IReview> items) {
        Collection c = items.values();
        reviews = new Review[c.size()];
        c.toArray(reviews);
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