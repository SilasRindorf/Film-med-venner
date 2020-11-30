package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.interfaces.IWatchedlistItem;

import java.util.List;

public class WatchedlistAdapter extends BaseAdapter {
    private Context ctx;
    private List<IWatchedlistItem> watchedlistItems;

    public WatchedlistAdapter(Context ctx, List<IWatchedlistItem> watchlistItems) {
        this.ctx = ctx;
        this.watchedlistItems = watchlistItems;
    }

    @Override
    public int getCount() {
        return watchedlistItems.size();
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
        IWatchedlistItem item = watchedlistItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //TODO item ting nedenunder
            gridView = inflater.inflate(R.layout.profile_watchedlist_item, null);
        }

        TextView description = gridView.findViewById(R.id.description);
        description.setText("You have " + item.getMovie().getTitle() + " to your watchedlist.");
        return gridView;
    }
}
