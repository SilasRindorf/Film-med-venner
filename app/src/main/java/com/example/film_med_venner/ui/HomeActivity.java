package com.example.film_med_venner.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DTO.FriendDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Review;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.ui.adapters.HomeAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;

import io.sentry.Sentry;


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
                    Sentry.captureMessage("HomeActivity->getToWatchList(uId:" + Controller_User.getInstance().getCurrentUser().getID() + ")" + ":  " + e.toString());
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

}