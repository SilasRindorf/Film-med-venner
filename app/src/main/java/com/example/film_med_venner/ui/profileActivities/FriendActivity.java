package com.example.film_med_venner.ui.profileActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.Utility;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.ui.adapters.FriendAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.controllers.Controller_Friends;
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
    private Button see_friendrequest_btn, add_friend_btn;
    private EditText searchField;
    private Executor bgThread = Executors.newSingleThreadExecutor();
    private Handler uiThread = new Handler();
    private Bundle bundle = new Bundle();


    IProfileController controller = Controller_Friends.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);

        gridView = findViewById(R.id.gridView);
        see_friendrequest_btn = findViewById(R.id.see_friendrequest_btn);
        see_friendrequest_btn.setOnClickListener(this);

        add_friend_btn = findViewById(R.id.add_friend_btn);
        add_friend_btn.setOnClickListener(this);

        searchField = findViewById(R.id.searchField);
        searchField.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        try {
                            AddFriend();
                        } catch (IDatabase.DatabaseException e) {
                            e.printStackTrace();
                        }
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        bgThread.execute(() -> {
            List<IProfile> friendList = new ArrayList<>();
            uiThread.post(() -> {
                friendAdapter = new FriendAdapter(ctx, friendList);
                gridView.setAdapter(friendAdapter);
                gridView.setVisibility(View.VISIBLE);
            });
            try {
                Controller_Friends.getInstance().getFriendRequest(1, friends -> {
                    friendAdapter.addItem(friends);
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == see_friendrequest_btn) {
            setContentView(R.layout.activity_friend_request);
            Intent intent = new Intent(this, FriendRequestActivity.class);
            startActivity(intent);
        }
        if (v == add_friend_btn){
            try {
                AddFriend();
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        }

    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }
    private void AddFriend() throws IDatabase.DatabaseException {
        Controller_Friends.getInstance().sendFriendRequest(searchField.getText().toString());
        searchField.setText("");
        Utility.hideKeyboard(FriendActivity.this);
    }
}