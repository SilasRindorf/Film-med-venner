package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.R;
import com.example.film_med_venner.interfaces.IProfile;

import java.util.List;

public class FriendRequestAdapter extends BaseAdapter {
    private Context ctx;
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

    //TODO Pt. henter den bare sample data. Den skal have fat på Databasen, og se hvem der har sent request til en.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        IProfile item = profileItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.profile_friend_item, null);
        }

        ImageView profilePicture = gridView.findViewById(R.id.imageView_profile);
        TextView profileName = gridView.findViewById(R.id.profile_name);
        TextView profileReviews = gridView.findViewById(R.id.profile_reviews);
        TextView profileRatings = gridView.findViewById(R.id.profile_ratings);
        TextView profileToWatchlist = gridView.findViewById(R.id.profile_to_watch_list);
        TextView profileWatchedlist = gridView.findViewById(R.id.profile_watched_list);

        //TODO Det her skal hente profilbilledet senere, men i det at vi ikke implementeret noget SOME ish endnu giver det først mening at lave senere.
        profilePicture.setImageResource(R.drawable.icon_profilepicture);
        profileName.setText(((Profile) item).getName());
        profileReviews.setText("- " + ((Profile) item).getAmountOfMoviesReviewed() + " reviewed movies.");
        profileRatings.setText("- " + ((Profile) item).getAmountOfMoviesRated() + " rated movies.");
        profileToWatchlist.setText("- " + ((Profile) item).getAmountOfMoviesOnToWatchList() + " movies on their to watchlist.");
        profileWatchedlist.setText("- " + ((Profile) item).getAmountOfMoviesOnWatchedList() + " movies on their watched list.");
        return gridView;
    }
}
