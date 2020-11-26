package com.example.film_med_venner.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.R;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;

import java.util.List;

public class RatingAdapter extends BaseAdapter {
    private Context ctx;
    private List<IRating> ratingItems;

    public RatingAdapter(Context ctx, List<IRating> ratingItems) {
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
        View gridView = convertView;
        IRating item = ratingItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.profile_rating_item, null);
        }
        TextView ratingText = gridView.findViewById(R.id.ratingtext);
        //TODO ((Rating) item).getMovie() Virker ikke optimalt. Når man kører den kommer der ikke det som forventes.
        ratingText.setText("You rated " + ((Rating) item).getMovie() + " " + ((Rating) item).getRating() +" stars");

        ImageView star1 = gridView.findViewById(R.id.ImageView_star_1);
        ImageView star2 = gridView.findViewById(R.id.ImageView_star_2);
        ImageView star3 = gridView.findViewById(R.id.ImageView_star_3);
        ImageView star4 = gridView.findViewById(R.id.ImageView_star_4);
        ImageView star5 = gridView.findViewById(R.id.ImageView_star_5);

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

        return gridView;
    }
}
