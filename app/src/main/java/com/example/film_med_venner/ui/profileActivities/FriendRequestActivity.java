package com.example.film_med_venner.ui.profileActivities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.ui.adapters.FriendRequestAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FriendRequestActivity extends AppCompatActivity {

    private GridView gridView;
    private FriendRequestAdapter friendRequestAdapter;
    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();
    private Context ctx;
    private List<FullProfileDTO> friendList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);

        String userID = Controller_User.getInstance().getCurrentUser().getID();

        ctx = this;
        gridView = findViewById(R.id.gridView);

        bgThread.execute(() -> {

            friendRequestAdapter = new FriendRequestAdapter(ctx, friendList);
            gridView.setAdapter(friendRequestAdapter);
            gridView.setVisibility(View.VISIBLE);

            try {
                Controller_Friends.getInstance().getFriendType(userID,0, friendRequest -> {
                    friendRequestAdapter.addItem(friendRequest);
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });
    }

    public void onClickAccept(View view) {
        try {
            int position = gridView.getPositionForView(view);
            Controller_Friends.getInstance().respondToFriendRequest(friendList.get(position).getID(), 1, () -> {
                friendRequestAdapter.removeItem(position);
                Toast.makeText(ctx, "Friend request accepted", Toast.LENGTH_LONG).show();
            });
        } catch (IDatabase.DatabaseException e) {
            e.printStackTrace();
            Toast.makeText(ctx, "Error accepting friend request", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickDecline(View view) {
        try {
            int position = gridView.getPositionForView(view);
            Controller_Friends.getInstance().respondToFriendRequest(friendList.get(position).getID(), -1, () -> {
                friendRequestAdapter.removeItem(position);
                Toast.makeText(ctx, "Friend request decline", Toast.LENGTH_LONG).show();
            });
        } catch (IDatabase.DatabaseException e) {
            e.printStackTrace();
            Toast.makeText(ctx, "Error declining friend request", Toast.LENGTH_LONG).show();
        }
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

}