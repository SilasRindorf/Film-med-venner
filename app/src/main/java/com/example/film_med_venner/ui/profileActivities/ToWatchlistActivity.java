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
import android.widget.Toast;

import com.example.film_med_venner.DTO.WatchItemDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_HomeFeed;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IWatchItem;
import com.example.film_med_venner.ui.MovieDetailsActivity;
import com.example.film_med_venner.ui.adapters.ToWatchlistAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.sentry.Sentry;

public class ToWatchlistActivity extends AppCompatActivity {
    private GridView gridView;
    private ToWatchlistAdapter toWatchlistAdapter;
    private Context ctx;
    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();
    private String userID;
    private List<IWatchItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_watchlist);

        ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        gridView = findViewById(R.id.gridView);

        Intent intent = getIntent();

        if (intent.getStringExtra("userID") == null || intent.getStringExtra("userID").equals(Controller_User.getInstance().getCurrentUser().getID())) {
            userID = Controller_User.getInstance().getCurrentUser().getID();
        } else {
            userID = intent.getStringExtra("userID");
        }

        bgThread.execute(() -> {
            try {
                Controller_HomeFeed.getInstance().getToWatchList(userID, watchList -> {
                    items = Arrays.asList(watchList);
                    uiThread.post(() -> {
                        toWatchlistAdapter = new ToWatchlistAdapter(ctx, items);
                        gridView.setAdapter(toWatchlistAdapter);
                        gridView.setVisibility(View.VISIBLE);
                    });
                });
            } catch (IDatabase.DatabaseException e) {
                Sentry.captureMessage("ToWatchListActivity->getToWatchList(uId:" + Controller_User.getInstance().getCurrentUser().getID() + ")" + ":  " + e.toString());
            }
        });

    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    public void itemOnClick(View view) {
        int position = gridView.getPositionForView(view);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("Id", items.get(position).getMovieIDStr());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void removeOnClick(View view) {

    }

}