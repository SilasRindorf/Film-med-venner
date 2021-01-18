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
import com.example.film_med_venner.interfaces.IWatchItem;
import com.example.film_med_venner.ui.adapters.WatchedlistAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.interfaces.IController.IProfileController;

import java.util.ArrayList;
import java.util.List;

public class WatchedlistActivity extends AppCompatActivity {
    GridView gridView;
    private WatchedlistAdapter watchedlistAdapter;
    private Context ctx;
    IProfileController controller = Controller_Friends.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchedlist);

        ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        gridView = findViewById(R.id.gridView);
        //TODO ToWatchListActivity not working
        /*bgThread.execute(() -> {
            try {
            List<IWatchItem> items = new ArrayList<>();
                items = controller.getWatchedListItems();
                    uiThread.post(() -> {
                       watchedlistAdapter = new WatchedlistAdapter(ctx, items);
                gridView.setAdapter(watchedlistAdapter);
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
}