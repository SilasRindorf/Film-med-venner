package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.interfaces.IDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FriendAdapter extends BaseAdapter {
    private final Context ctx;
    private final List<FullProfileDTO> profileItems;

    public FriendAdapter(Context ctx, List<FullProfileDTO> profileItems) {
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
        FullProfileDTO item = profileItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.profile_friend_item, null);
        }

        ImageView profilePicture = gridView.findViewById(R.id.imageView_profile);
        TextView profileName = gridView.findViewById(R.id.profile_name);
        TextView profileRatings = gridView.findViewById(R.id.profile_ratings);
        TextView profileToWatchlist = gridView.findViewById(R.id.profile_to_watch_list);
        TextView profileWatchedlist = gridView.findViewById(R.id.profile_watched_list);



        Picasso.get().load(item.getPictureURL()).into(profilePicture);
        profileName.setText(item.getName());
        profileRatings.setText(item.getReviews().size() + " reviewed movies.");
        profileToWatchlist.setText(item.getToWatchList().size() + " movies on their to watchlist.");
        profileWatchedlist.setText(item.getWatchedList().size() + " movies on their watched list.");
        return gridView;
    }

    public void addItem(FullProfileDTO p) {
        profileItems.add(p);
        this.notifyDataSetChanged();
    }

}
