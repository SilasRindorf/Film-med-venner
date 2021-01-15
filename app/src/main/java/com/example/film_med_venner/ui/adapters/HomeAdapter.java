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

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Movie;
import com.example.film_med_venner.interfaces.IHomeFeedItems;
import com.example.film_med_venner.ui.HomeActivity;
import com.example.film_med_venner.ui.profileActivities.ReviewActivity;

import org.w3c.dom.Text;

import java.util.List;

public class HomeAdapter extends BaseAdapter {
    private Context ctx;
    private List<IHomeFeedItems> homeFeedItems;
    private String reviewText;

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

        View listView = convertView;

        IHomeFeedItems item = homeFeedItems.get(position);

        if (item instanceof Review){

            if (listView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                listView = inflater.inflate(R.layout.feed_rated_item, null);
            }
            

            TextView description = listView.findViewById(R.id.description);
            TextView textView_review_short = listView.findViewById(R.id.textView_review_short);

            ImageView star1 = listView.findViewById(R.id.ImageView_star_1);
            ImageView star2 = listView.findViewById(R.id.ImageView_star_2);
            ImageView star3 = listView.findViewById(R.id.ImageView_star_3);
            ImageView star4 = listView.findViewById(R.id.ImageView_star_4);
            ImageView star5 = listView.findViewById(R.id.ImageView_star_5);
            description.setText(item.getUsername() + " has rated " + (item.getMovieIDStr()) + " with " +
                    ((Review) item).getRating() + " stars.");

            reviewText = ((Review) item).getReview();
            if (reviewText.length() > 200){
                textView_review_short.setText(reviewText.substring(0,200) + "...");
                //textView_review_short.setOnClickListener((View.OnClickListener) this);
            } else {
                textView_review_short.setText(reviewText);
            }



            if (((Review) item).getRating() == 0){
                star1.setImageResource(R.drawable.icon_empty_star);
                star2.setImageResource(R.drawable.icon_empty_star);
                star3.setImageResource(R.drawable.icon_empty_star);
                star4.setImageResource(R.drawable.icon_empty_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Review) item).getRating() == 1){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_empty_star);
                star3.setImageResource(R.drawable.icon_empty_star);
                star4.setImageResource(R.drawable.icon_empty_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Review) item).getRating() == 2){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_empty_star);
                star4.setImageResource(R.drawable.icon_empty_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Review) item).getRating() == 3){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_filled_star);
                star4.setImageResource(R.drawable.icon_empty_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Review) item).getRating() == 4){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_filled_star);
                star4.setImageResource(R.drawable.icon_filled_star);
                star5.setImageResource(R.drawable.icon_empty_star);
            }
            else if (((Review) item).getRating() == 5){
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_filled_star);
                star4.setImageResource(R.drawable.icon_filled_star);
                star5.setImageResource(R.drawable.icon_filled_star);
            }

        }
        /*else if (item instanceof WatchItem){

            if (listView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                listView = inflater.inflate(R.layout.feed_added_to_watch_list_item, null);
            }

            TextView description = listView.findViewById(R.id.description);


            description.setText(item.getUsername() + " has added " + Controller_Movie.getInstance().getMovies()[item.getMovieID()].getTitle() + " to their to watchlist.");

        }*/
        else {
            // Throw error.
        }

        return listView;
    }

}

