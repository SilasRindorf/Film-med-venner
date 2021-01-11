package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Movie;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.ui.HomeActivity;
import com.example.film_med_venner.ui.profileActivities.RatingActivity;

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
            ImageButton moviePoster = (ImageButton) gridView.findViewById(R.id.imageView_moviePoster);
            //moviePoster.setOnClickListener(this);

            TextView description = gridView.findViewById(R.id.description);

            ImageView star1 = gridView.findViewById(R.id.ImageView_star_1);
            ImageView star2 = gridView.findViewById(R.id.ImageView_star_2);
            ImageView star3 = gridView.findViewById(R.id.ImageView_star_3);
            ImageView star4 = gridView.findViewById(R.id.ImageView_star_4);
            ImageView star5 = gridView.findViewById(R.id.ImageView_star_5);

            description.setText(item.getUsername() + " has rated " + Controller_Movie.getInstance()
                    .getMovies()[item.getMovieID()].getTitle() + " with " +
                    ((Rating) item).getRating() + " stars.");

            if (((Rating) item).getRating() == 0){
                star1.setImageResource(R.drawable.icon_empty_star);
                star2.setImageResource(R.drawable.icon_empty_star);
                star3.setImageResource(R.drawable.icon_empty_star);
                star4.setImageResource(R.drawable.icon_empty_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Rating) item).getRating() == 1){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_empty_star);
                star3.setImageResource(R.drawable.icon_empty_star);
                star4.setImageResource(R.drawable.icon_empty_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Rating) item).getRating() == 2){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_empty_star);
                star4.setImageResource(R.drawable.icon_empty_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Rating) item).getRating() == 3){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_filled_star);
                star4.setImageResource(R.drawable.icon_empty_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Rating) item).getRating() == 4){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_filled_star);
                star4.setImageResource(R.drawable.icon_filled_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Rating) item).getRating() == 5){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_filled_star);
                star4.setImageResource(R.drawable.icon_filled_star);
                star5.setImageResource(R.drawable.icon_filled_star);
            }

        }
        /*else if (item instanceof WatchItem){

            if (gridView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                gridView = inflater.inflate(R.layout.feed_added_to_watch_list_item, null);
            }

            TextView description = gridView.findViewById(R.id.description);


            description.setText(item.getUsername() + " has added " + Controller_Movie.getInstance().getMovies()[item.getMovieID()].getTitle() + " to their to watchlist.");

        }*/
        else {
            // Throw error.
        }

        return gridView;
    }

}

