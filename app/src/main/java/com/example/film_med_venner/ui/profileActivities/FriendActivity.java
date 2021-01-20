package com.example.film_med_venner.ui.profileActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.Utility;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.ui.LoadingScreen;
import com.example.film_med_venner.ui.ProfileActivity;
import com.example.film_med_venner.ui.adapters.FriendAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.controllers.Controller_Friends;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FriendActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView gridView;
    private FriendAdapter friendAdapter;
    private Context ctx;
    private Intent intent;
    private Button see_friendrequest_btn, add_friend_btn/*, copy_id_btn*/;
    private EditText searchField;
    private LinearLayout l_layout_buttons;
    private Executor bgThread = Executors.newSingleThreadExecutor();
    private Handler uiThread = new Handler();
    private Bundle bundle = new Bundle();
    private List<FullProfileDTO> friendList = new ArrayList<>();
    private String userID;
    private int amountOfFriendRequest;
    //private TextView profile_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);

        findViews();
    }


    @Override
    public void onClick(View v) {
        if (v == see_friendrequest_btn) {
            setContentView(R.layout.activity_friend_request);
            Intent intent = new Intent(this, FriendRequestActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
        }
        if (v == add_friend_btn) {
            try {
                AddFriend();
            } catch (IDatabase.DatabaseException e) {
                Toast.makeText(this,"Error adding friend",Toast.LENGTH_LONG).show();
            }
        }
        /*if (v == copy_id_btn){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Your ID", userID);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(ctx, "ID: \"" + userID + " added to clipboard.", Toast.LENGTH_LONG).show();
        }*/
    }

    public void itemOnClick(View view) {
        int position = gridView.getPositionForView(view);
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("userID", friendList.get(position).getID());
        startActivity(intent);
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    private void AddFriend() throws IDatabase.DatabaseException {
        Controller_Friends.getInstance().sendFriendRequestByMail(searchField.getText().toString());
        searchField.setText("");
        Utility.hideKeyboard(FriendActivity.this);
    }

    private void findViews() {
        //profile_id = findViewById(R.id.profile_id);
        gridView = findViewById(R.id.listView);
        see_friendrequest_btn = findViewById(R.id.see_friendrequest_btn);
        searchField = findViewById(R.id.searchField);
        add_friend_btn = findViewById(R.id.add_friend_btn);
        l_layout_buttons = findViewById(R.id.layout_buttons);

        ctx = this;

        intent = getIntent();

        if (intent.getStringExtra("userID") == null || intent.getStringExtra("userID").equals(Controller_User.getInstance().getCurrentUser().getID())) {
            userID = Controller_User.getInstance().getCurrentUser().getID();
            //profile_id.setText(userID);
        } else {
            userID = intent.getStringExtra("userID");
            l_layout_buttons.getLayoutParams().height = 0;
            //profile_id.setText(userID);
        }
        amountOfFriendRequest = intent.getIntExtra("friendRequests",0);
        if (amountOfFriendRequest == 0){
            see_friendrequest_btn.setText("You have 0 new friend requests");
        } else if (amountOfFriendRequest == 1){
            see_friendrequest_btn.setText("You have 1 new friend requests");
        } else if (amountOfFriendRequest > 1){
            see_friendrequest_btn.setText("You have " + amountOfFriendRequest  + " new friend requests");
        }

        see_friendrequest_btn.setOnClickListener(this);
        add_friend_btn.setOnClickListener(this);

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

        friendAdapter = new FriendAdapter(ctx, friendList);
        gridView.setAdapter(friendAdapter);
        gridView.setVisibility(View.VISIBLE);

        bgThread.execute(() -> {

            try {
                Controller_Friends.getInstance().getFriendType(userID,1, friends -> {
                    friendAdapter.addItem(friends);
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });
        /*copy_id_btn = findViewById(R.id.copy_id_btn);
        copy_id_btn.setOnClickListener(this);*/
    }
}