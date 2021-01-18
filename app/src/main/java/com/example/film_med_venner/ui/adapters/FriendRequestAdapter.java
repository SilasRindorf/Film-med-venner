package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.example.film_med_venner.ui.profileActivities.FriendRequestActivity;

import java.util.List;

public class FriendRequestAdapter extends BaseAdapter {
private Context ctx;
private Controller_Friends cf;
private List<IProfile> profileItems;

public FriendRequestAdapter(Context ctx, List<IProfile> profileItems) {
        this.ctx = ctx;
        this.profileItems = profileItems;
        }

@Override
public int getCount() {
        return profileItems.size();
        }

@Override
public Object getItem(int position) {
        return position;
        }

@Override
public long getItemId(int i) {
        return i;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        IProfile item = profileItems.get(position);
        if (gridView == null) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gridView = inflater.inflate(R.layout.profile_friend_request_item, null);
        }

        TextView friend_request_name = gridView.findViewById(R.id.textView_fr_name);
        friend_request_name.setText(item.getName());
        ImageButton accept_btn = gridView.findViewById(R.id.btn_accept);
        accept_btn.setOnClickListener(v -> {
                try {
                        cf.getInstance().respondToFriendRequest(item.getID(), 1, () ->
                                Toast.makeText(ctx, "Friend request accepted", Toast.LENGTH_LONG).show());
                } catch (IDatabase.DatabaseException e) {
                        e.printStackTrace();
                        Toast.makeText(ctx, "Error accepting friend request", Toast.LENGTH_LONG).show();
                }
        });

        ImageButton decline_btn = gridView.findViewById(R.id.btn_decline);
        decline_btn.setOnClickListener(v -> {
                try {
                        cf.getInstance().respondToFriendRequest(item.getID(), -1, () ->
                                Toast.makeText(ctx, "Friend request decline", Toast.LENGTH_LONG).show());
                } catch (IDatabase.DatabaseException e) {
                        e.printStackTrace();
                        Toast.makeText(ctx, "Error declining friend request", Toast.LENGTH_LONG).show();
                }
        });

        ImageView profilePicture = gridView.findViewById(R.id.imageView_profile);


        //TODO Det her skal hente profilbilledet senere, men i det at vi ikke implementeret noget SOME ish endnu giver det først mening at lave senere.
        try {
        profilePicture.setImageResource(R.drawable.icon_profilepicture);
        friend_request_name.setText(((Profile) item).getName());
        } catch (NullPointerException e){
        System.out.println(e);
        }
        return gridView;
        }
        }
