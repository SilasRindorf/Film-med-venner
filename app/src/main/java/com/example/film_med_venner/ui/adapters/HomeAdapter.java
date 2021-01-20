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
import java.util.TreeMap;

public class HomeAdapter extends BaseAdapter {
    private final Context ctx;
    private final TreeMap<Date, IReview> homeFeedItems;
    private View listView;
    private TextView profileName, review, creationDate, movieTitle;
    private ImageView moviePoster, star1, star2, star3, star4, star5;
    private Movie movie;


    public HomeAdapter(Context ctx, TreeMap<Date, IReview> items) {
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
        movieTitle.setText(movie.getTitle());
        review.setText(item.getReview());
        starfest(item.getRating());
        creationDate.setText(item.getCreationDate().toString());

        return listView;
    }


    private void findViews() {
        moviePoster = listView.findViewById(R.id.moviePoster);
        profileName = listView.findViewById(R.id.profile_name);
        movieTitle = listView.findViewById(R.id.movie_title);
        review = listView.findViewById(R.id.textView_review);
        star1 = listView.findViewById(R.id.ImageView_star_1);
        star2 = listView.findViewById(R.id.ImageView_star_2);
        star3 = listView.findViewById(R.id.ImageView_star_3);
        star4 = listView.findViewById(R.id.ImageView_star_4);
        star5 = listView.findViewById(R.id.ImageView_star_5);
        creationDate = listView.findViewById(R.id.creation_date);
    }

    private void starfest(int rating) {
        star1.setImageResource(R.drawable.icon_empty_star);
        star2.setImageResource(R.drawable.icon_empty_star);
        star3.setImageResource(R.drawable.icon_empty_star);
        star4.setImageResource(R.drawable.icon_empty_star);
        star5.setImageResource(R.drawable.icon_empty_star);
        switch (rating) {
            case 5:
                star5.setImageResource(R.drawable.icon_filled_star);
            case 4:
                star4.setImageResource(R.drawable.icon_filled_star);
            case 3:
                star3.setImageResource(R.drawable.icon_filled_star);
            case 2:
                star2.setImageResource(R.drawable.icon_filled_star);
            case 1:
                star1.setImageResource(R.drawable.icon_filled_star);

        }
    }

}

