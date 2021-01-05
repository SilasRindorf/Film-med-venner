package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Movie;
import com.example.film_med_venner.interfaces.IHomeFeedItems;

import java.util.List;

public class HomeAdapter extends BaseAdapter {
    private Context ctx;
    private List<IHomeFeedItems> homeFeedItems;

    public HomeAdapter(Context ctx, List<IHomeFeedItems> items) {
        this.ctx = ctx;
        this.homeFeedItems = items;
    }

    @Override
    public int getCount() {
        return homeFeedItems.size();
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

        IHomeFeedItems item = homeFeedItems.get(position);

        if (item instanceof Rating){

            if (gridView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                gridView = inflater.inflate(R.layout.feed_rated_item, null);
            }

            TextView description = gridView.findViewById(R.id.description);

            description.setText(item.getUsername() + " has rated " + Controller_Movie.getInstance().getMovies()[item.getMovieID()].getTitle() + " with " + ((Rating) item).getRating() + " stars.");

        }
        else if (item instanceof Review){

            if (gridView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                gridView = inflater.inflate(R.layout.feed_review_item, null);
            }

            TextView description = gridView.findViewById(R.id.description);
            TextView reviewText = gridView.findViewById(R.id.reviewtext);

            description.setText(item.getUsername() + " has reviewed " + Controller_Movie.getInstance().getMovies()[item.getMovieID()].getTitle() + ":");
            reviewText.setText(((Review) item).getReview());

        }
        else if (item instanceof WatchItem){

            if (gridView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                gridView = inflater.inflate(R.layout.feed_added_to_watch_list_item, null);
            }

            TextView description = gridView.findViewById(R.id.description);


            description.setText(item.getUsername() + " has added " + Controller_Movie.getInstance().getMovies()[item.getMovieID()].getTitle() + " to their to watchlist.");

        }
        else {
            // Throw error.
        }

        return gridView;
    }

}

