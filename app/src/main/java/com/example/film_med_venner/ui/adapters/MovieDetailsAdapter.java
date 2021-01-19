package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.interfaces.IReview;

import java.util.List;

public class MovieDetailsAdapter extends BaseAdapter {
    private Context ctx;
    private List<IReview> ratingItems;
    private TextView friend_name, review_text;
    private ImageView profile_pic, star1, star2, star3, star4, star5;

    public MovieDetailsAdapter(Context ctx, List<IReview> ratingItems) {
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
        IReview item = ratingItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.item_review, null);
        }
        friend_name = gridView.findViewById(R.id.friend_name);
        review_text = gridView.findViewById(R.id.review_text);
        //TODO Tilføj noget med at anskaffe profilbillede
        profile_pic = gridView.findViewById(R.id.profile_pic);
        friend_name.setText(item.getUsername() + " rated: ");
        review_text.setText(item.getReview());

        star1 = gridView.findViewById(R.id.ImageView_star_1);
        star2 = gridView.findViewById(R.id.ImageView_star_2);
        star3 = gridView.findViewById(R.id.ImageView_star_3);
        star4 = gridView.findViewById(R.id.ImageView_star_4);
        star5 = gridView.findViewById(R.id.ImageView_star_5);



        return gridView;
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