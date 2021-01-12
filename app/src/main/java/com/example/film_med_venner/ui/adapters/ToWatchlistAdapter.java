package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Movie;
import com.example.film_med_venner.interfaces.IWatchItem;

import java.util.List;

public class ToWatchlistAdapter extends BaseAdapter {
    private Context ctx;
    private List<IWatchItem> watchlistItems;

    public ToWatchlistAdapter(Context ctx, List<IWatchItem> watchlistItems) {
        this.ctx = ctx;
        this.watchlistItems = watchlistItems;
    }

    @Override
    public int getCount() {
        return watchlistItems.size();
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
        IWatchItem item = watchlistItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //TODO item ting nedenunder
            gridView = inflater.inflate(R.layout.movie_item, null);
        }

        TextView description = gridView.findViewById(R.id.description);
        description.setText("You have " + Controller_Movie.getInstance().getMovies()[item.getMovieID()].getTitle() + " on your to watchlist.");
        return gridView;
    }
}
