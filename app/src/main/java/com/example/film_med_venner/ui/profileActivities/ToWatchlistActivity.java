package com.example.film_med_venner.ui.profileActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.controllers.Controller_HomeFeed;
import com.example.film_med_venner.ui.adapters.ToWatchlistAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IWatchItem;

import java.util.ArrayList;
import java.util.List;

public class ToWatchlistActivity extends AppCompatActivity {
    GridView gridView;
    private ToWatchlistAdapter toWatchlistAdapter;
    private Context ctx;
    Controller_HomeFeed controller = Controller_HomeFeed.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_watchlist);

        ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        gridView = findViewById(R.id.gridView);
        //TODO ToWatchListActivity not working
        /*bgThread.execute(() -> {
            try {
                List<IWatchItem> items = new ArrayList<>();
                items = controller.getToWatchlistItems();
                    uiThread.post(() -> {
                       toWatchlistAdapter = new ToWatchlistAdapter(ctx, items);
                gridView.setAdapter(toWatchlistAdapter);
                gridView.setVisibility(View.VISIBLE);
                    });
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });*/

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

            String errorMsg = null;

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Object doInBackground(Object... arg0) {
                try {

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

            }

        };

        if (run) {
            asyncTask.execute();
        } else {
            asyncTask.cancel(true);
        }
    }
}