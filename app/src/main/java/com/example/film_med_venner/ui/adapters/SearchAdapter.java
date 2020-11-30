package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.interfaces.ISearch;

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

        category.setText(item.getCategory());
        moviePoster1.setImageResource(R.drawable.mp_batman_begins);
        moviePoster2.setImageResource(R.drawable.mp_batman_tdkr);
        moviePoster3.setImageResource(R.drawable.mp_batman_the_dark_knight);
        moviePoster4.setImageResource(R.drawable.mp_inception);
        moviePoster5.setImageResource(R.drawable.mp_seven);
        moviePoster6.setImageResource(R.drawable.mp_sherlock_holmes);
        moviePoster7.setImageResource(R.drawable.mp_the_irishman);
        moviePoster8.setImageResource(R.drawable.mp_the_social_dilemma);
        moviePoster9.setImageResource(R.drawable.mp_the_wolf_of_wallstreet);
        moviePoster10.setImageResource(R.drawable.mp_watchmen);

        return gridView;
    }
}
