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
import com.example.film_med_venner.interfaces.IReview;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {
    private final Context ctx;
    private final List<IReview> ratingItems;
    private ImageView star1, star2, star3, star4, star5, moviePoster;
    private TextView movieTitle, reviewText;
    private View gridView;

    public ReviewAdapter(Context ctx, List<IReview> ratingItems) {
        this.ctx = ctx;
        this.ratingItems = ratingItems;
    }

    @Override
    public int getCount() {
        return ratingItems.size();
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
        IReview item = ratingItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.item_your_review, null);
        }
        reviewText = gridView.findViewById(R.id.ratingtext);
        //TODO ((Review) item).getMovie() Virker ikke optimalt. Når man kører den kommer der ikke det som forventes.

        findViews();
        Movie movie = Controller_MovieDetails.getInstance().getMovie(item.getMovieIDStr());

        Picasso.get().load(movie.getPoster()).into(moviePoster);
        movieTitle.setText(movie.getTitle());
        reviewText.setText(item.getReview());

        star1.setImageResource(R.drawable.icon_empty_star);
        star2.setImageResource(R.drawable.icon_empty_star);
        star3.setImageResource(R.drawable.icon_empty_star);
        star4.setImageResource(R.drawable.icon_empty_star);
        star5.setImageResource(R.drawable.icon_empty_star);
        switch (item.getRating()) {
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

        return gridView;
    }

    private void findViews() {
        movieTitle = gridView.findViewById(R.id.movieTitle);
        reviewText = gridView.findViewById(R.id.review_text);
        star1 = gridView.findViewById(R.id.ImageView_star_1);
        star2 = gridView.findViewById(R.id.ImageView_star_2);
        star3 = gridView.findViewById(R.id.ImageView_star_3);
        star4 = gridView.findViewById(R.id.ImageView_star_4);
        star5 = gridView.findViewById(R.id.ImageView_star_5);
        moviePoster = gridView.findViewById(R.id.moviePoster);
    }
}
