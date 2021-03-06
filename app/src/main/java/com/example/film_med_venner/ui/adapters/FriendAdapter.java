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
import com.squareup.picasso.Picasso;

import java.util.List;

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
        profileToWatchlist.setText(item.getToWatchList().size() + " movies on their to watch list.");
        profileWatchedlist.setText("has watched " + item.getWatchedList().size() + " movies on their watched list.");

        String full;

        switch (item.getReviews().size()) {
            case 0:
                full = "Has not reviewed any movies.";
                break;
            case 1:
                full = "1 movie reviewed.";
                break;
            default:
                full = item.getReviews().size() + " movies reviewed.";
        }
        profileRatings.setText(full);

        switch (item.getToWatchList().size()) {
            case 0:
                full = "No movies on their to watch list.";
                break;
            case 1:
                full = "1 movie on their to watch list.";
                break;
            default:
                full = item.getToWatchList().size() + " movies on their to watch list.";
        }
        profileToWatchlist.setText(full);

        switch (item.getWatchedList().size()) {
            case 0:
                full = "has not watched any movies yet.";
                break;
            case 1:
                full = "1 movie watched.";
                break;
            default:
                full = item.getWatchedList().size() + " movies watched.";
        }
        profileWatchedlist.setText(full);


        return gridView;
    }

    public void addItem(FullProfileDTO p) {
        profileItems.add(p);
        this.notifyDataSetChanged();
    }


}
