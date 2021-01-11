package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.API.OmdbWebServiceClient;
import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.R;
import com.example.film_med_venner.interfaces.ISearch;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private Context ctx;
    private List<ISearch> search;

    public SearchAdapter(Context ctx, List<ISearch> search) {
        this.ctx = ctx;
        this.search = search;
    }

    @Override
    public int getCount() {
        return search.size();
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
        ISearch item = search.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //TODO item ting nedenunder
            gridView = inflater.inflate(R.layout.scrollable_movie_bar, null);
        }
        TextView category = gridView.findViewById(R.id.movie_category_title);
        ImageView moviePoster1 = gridView.findViewById(R.id.moviePoster1);
        ImageView moviePoster2 = gridView.findViewById(R.id.moviePoster2);
        ImageView moviePoster3 = gridView.findViewById(R.id.moviePoster3);
        ImageView moviePoster4 = gridView.findViewById(R.id.moviePoster4);
        ImageView moviePoster5 = gridView.findViewById(R.id.moviePoster5);
        ImageView moviePoster6 = gridView.findViewById(R.id.moviePoster6);
        ImageView moviePoster7 = gridView.findViewById(R.id.moviePoster7);
        ImageView moviePoster8 = gridView.findViewById(R.id.moviePoster8);
        ImageView moviePoster9 = gridView.findViewById(R.id.moviePoster9);
        ImageView moviePoster10 = gridView.findViewById(R.id.moviePoster10);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        List<Movie> movieList = OmdbWebServiceClient.searchMovieByTitle("my little pony", 1);

        category.setText(item.getCategory());

        Picasso.get().load(movieList.get(0).getPoster()).into(moviePoster1);
        Picasso.get().load(movieList.get(1).getPoster()).into(moviePoster2);
        Picasso.get().load(movieList.get(2).getPoster()).into(moviePoster3);
        Picasso.get().load(movieList.get(3).getPoster()).into(moviePoster4);
        Picasso.get().load(movieList.get(4).getPoster()).into(moviePoster5);
        Picasso.get().load(movieList.get(5).getPoster()).into(moviePoster6);
        Picasso.get().load(movieList.get(6).getPoster()).into(moviePoster7);
        Picasso.get().load(movieList.get(7).getPoster()).into(moviePoster8);
        Picasso.get().load(movieList.get(8).getPoster()).into(moviePoster9);
        Picasso.get().load(movieList.get(9).getPoster()).into(moviePoster10);


        return gridView;
    }
}
