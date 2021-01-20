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
import com.example.film_med_venner.interfaces.IMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private Context ctx;
    private List<Movie> movie;

    public SearchAdapter(Context ctx, List<Movie> search) {
        this.ctx = ctx;
        this.movie = search;
    }

    @Override
    public int getCount() {
        return movie.size();
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
        IMovie item = movie.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.search_item, null);
        }

        ImageView moviePoster = gridView.findViewById(R.id.moviePoster);
        TextView title = gridView.findViewById(R.id.searchTitle);
        TextView type = gridView.findViewById(R.id.type);
        TextView year = gridView.findViewById(R.id.year);

        if (item.getPoster() == "N/A") {
            Picasso.get().load(R.drawable.mp).into(moviePoster);
        } else {
            Picasso.get().load(item.getPoster()).into(moviePoster);
        }
        title.setText(item.getTitle());
        type.setText(item.getType());
        year.setText(item.getYear());

        return gridView;
    }
}
