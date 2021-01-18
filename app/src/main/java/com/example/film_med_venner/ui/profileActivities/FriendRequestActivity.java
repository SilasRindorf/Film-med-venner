package com.example.film_med_venner.ui.profileActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.runnable.RunnableProfileUI;
import com.example.film_med_venner.ui.adapters.FriendRequestAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FriendRequestActivity extends AppCompatActivity {

    private GridView gridView;
    private FriendRequestAdapter friendRequestAdapter;
    private Executor bgThread = Executors.newSingleThreadExecutor();
    private Handler uiThread = new Handler();
    private Context ctx;
    private List<IProfile> items = new ArrayList<IProfile>();
    private IProfileController controller = Controller_Friends.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        ctx = this;
        gridView = findViewById(R.id.gridView);

       /*bgThread.execute(() -> {
            try {
                Controller_Friends.getInstance().getFriendRequests((RunnableProfileUI) friendRequest -> {
                    List<IProfile> friendList = Arrays.asList(friendRequest);
                    System.out.println("FriendRequest" + friendRequest);
                    uiThread.post(() -> {
                        friendRequestAdapter = new FriendRequestAdapter(ctx, friendList);
                        gridView.setAdapter(friendRequestAdapter);
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