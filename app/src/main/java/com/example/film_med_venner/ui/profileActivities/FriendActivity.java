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
import com.example.film_med_venner.ui.adapters.FriendAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.controllers.Controller_Profile;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity {
    GridView gridView;
    private FriendAdapter friendAdapter;
    private Context ctx;
    IProfileController controller = Controller_Profile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

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
            List<IProfile> items = new ArrayList<IProfile>();
            String errorMsg = null;

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    items = controller.getFriendItems();
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
                friendAdapter = new FriendAdapter(ctx, items);
                gridView.setAdapter(friendAdapter);
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