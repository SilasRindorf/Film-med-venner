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
import android.widget.Button;
import android.widget.GridView;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.R;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.ui.adapters.FriendAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.controllers.Controller_Profile;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FriendActivity extends AppCompatActivity implements View.OnClickListener {
    private GridView gridView;
    private FriendAdapter friendAdapter;
    private Context ctx;
    private Button see_friendrequest_btn;
    private Executor bgThread = Executors.newSingleThreadExecutor();
    private Handler uiThread = new Handler();
    private Bundle bundle = new Bundle();


    IProfileController controller = Controller_Profile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        gridView = findViewById(R.id.gridView);
        see_friendrequest_btn = findViewById(R.id.see_friendrequest_btn);
        see_friendrequest_btn.setOnClickListener(this);

        bgThread.execute(() -> {
            try {
                System.out.println("This is where you die the first time");
                //TODO YOU DIE HERE. SILAS FIX <3 8===>
                Database.getInstance().getFriends(friends -> {
                    List<IProfile> friendList = Arrays.asList(friends);
                    uiThread.post(() -> {
                        System.out.println("This is where you die: " + friendList.toString());
                        friendAdapter = new FriendAdapter(ctx, friendList);
                        gridView.setAdapter(friendAdapter);
                        gridView.setVisibility(View.VISIBLE);
                    });
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });

        /*bgThread.execute(() -> {
            try {
                if (Database.getInstance().getCurrentUser().getFriendIDs().length > 0){
                    return;
                } else {
                    Database.getInstance().getFriends(Database.getInstance().getCurrentUser().getID(),Database.getInstance().getFriends({

                    });
                    IRating newRating = new Rating(starRating, Database.getInstance().getCurrentUser().getName(), movieID, reviewInput.getText().toString(),Database.getInstance().getCurrentUser().getID());
                    friends = Database.getInstance().getFriends(Database.getInstance().getCurrentUser());

                    });
                    uiThread.post(() -> {

                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        if (v == see_friendrequest_btn){
            setContentView(R.layout.activity_friend_request);
            Intent intent = new Intent(this, FriendRequestActivity.class);
            startActivity(intent);
        }

    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    /*@Override
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
    }*/

}