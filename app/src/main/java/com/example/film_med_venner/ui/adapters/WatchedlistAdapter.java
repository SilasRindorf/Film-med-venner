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
import com.example.film_med_venner.controllers.Controller_Movie;
import com.example.film_med_venner.controllers.Controller_MovieDetails;
import com.example.film_med_venner.interfaces.IWatchItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WatchedlistAdapter extends BaseAdapter {
    private Context ctx;
    private List<IWatchItem> watchedlistItems;
    private TextView title, year, type;
    private ImageView moviePoster;
    private View gridView;
    private Movie movie;

    public WatchedlistAdapter(Context ctx, List<IWatchItem> watchlistItems) {
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
        gridView = convertView;
        IWatchItem item = watchedlistItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //TODO item ting nedenunder
            gridView = inflater.inflate(R.layout. profile_to_watch_item, null);
        }

        findViews();
        System.out.println(item.getMovieIDStr());
        movie = Controller_MovieDetails.getInstance().getMovie(item.getMovieIDStr());

        Picasso.get().load(movie.getPoster()).into(moviePoster);
        title.setText(movie.getTitle());
        year.setText(movie.getYear());
        type.setText(movie.getType());

        return gridView;
    }

    private void findViews() {
        moviePoster = gridView.findViewById(R.id.moviePoster);
        title = gridView.findViewById(R.id.title);
        year = gridView.findViewById(R.id.year);
        type = gridView.findViewById(R.id.type);
    }
}
