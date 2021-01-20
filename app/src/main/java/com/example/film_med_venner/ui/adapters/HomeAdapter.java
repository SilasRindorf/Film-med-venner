package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_MovieDetails;
import com.example.film_med_venner.interfaces.IReview;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class HomeAdapter extends BaseAdapter {
    private Context ctx;
    private TreeMap<Date, IReview> homeFeedItems;
    private View listView;
    private TextView profileName, review;
    private ImageView moviePoster, star1, star2, star3, star4, star5;
    private Movie movie;



    public HomeAdapter(Context ctx, TreeMap<Date, IReview> items, Date date) {
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

        listView = convertView;

        Collection c = homeFeedItems.values();

        Review[] reviews = new Review[c.size()];

        c.toArray(reviews);

        IReview item = reviews[position];

        if (listView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listView = inflater.inflate(R.layout.feed_rated_item, null);
        }

        findViews();

        movie = Controller_MovieDetails.getInstance().getMovie(item.getMovieIDStr());
        Picasso.get().load(movie.getPoster()).into(moviePoster);
        profileName.setText(item.getUsername());
        review.setText(item.getReview());
        starfest(item.getRating());

        return listView;
    }
    public void addItem(IReview review) {
        homeFeedItems.put(review.getCreationDate(), review);
        this.notifyDataSetChanged();
    }

    private void iterateMap() {
        Set set = homeFeedItems.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry item = (Map.Entry) i.next();
            item.getKey();
        }


    }



    private void findViews() {
        moviePoster = listView.findViewById(R.id.moviePoster);
        profileName = listView.findViewById(R.id.profile_name);
        review = listView.findViewById(R.id.textView_review);
        star1 = listView.findViewById(R.id.ImageView_star_1);
        star2 = listView.findViewById(R.id.ImageView_star_2);
        star3 = listView.findViewById(R.id.ImageView_star_3);
        star4 = listView.findViewById(R.id.ImageView_star_4);
        star5 = listView.findViewById(R.id.ImageView_star_5);
    }

    private void starfest(int rating) {
        if (rating == 0){
            star1.setImageResource(R.drawable.icon_empty_star);
            star2.setImageResource(R.drawable.icon_empty_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (rating == 1){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_empty_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (rating == 2){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (rating == 3){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (rating == 4){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_filled_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (rating == 5){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_filled_star);
            star5.setImageResource(R.drawable.icon_filled_star);
        }
    }

}

