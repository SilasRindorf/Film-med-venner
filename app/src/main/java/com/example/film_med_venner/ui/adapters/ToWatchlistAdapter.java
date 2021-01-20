package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_MovieDetails;
import com.example.film_med_venner.interfaces.IWatchItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ToWatchlistAdapter extends BaseAdapter {
    private Context ctx;
    private List<IWatchItem> watchlistItems;
    private Movie movie;

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
            gridView = inflater.inflate(R.layout.profile_to_watch_item, null);
        }

        ImageView moviePoster = gridView.findViewById(R.id.moviePoster);
        TextView title = gridView.findViewById(R.id.title);
        TextView year = gridView.findViewById(R.id.year);
        TextView type = gridView.findViewById(R.id.type);

        movie = Controller_MovieDetails.getInstance().getMovie(item.getMovieIDStr());

        Picasso.get().load(movie.getPoster()).into(moviePoster);
        title.setText(movie.getTitle());
        year.setText(movie.getYear());
        type.setText(movie.getType());

        return gridView;
    }

    public void removeItem(int position) {
        watchlistItems.remove(position);
        this.notifyDataSetChanged();
    }
}
