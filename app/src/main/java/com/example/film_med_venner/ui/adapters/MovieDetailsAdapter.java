package com.example.film_med_venner.ui.adapters;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IReview;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsAdapter extends BaseAdapter {
    private Context ctx;
    private List<IReview> ratingItems;
    private View gridView;
    private TextView friend_name, review_text;
    private ImageView profile_pic, star1, star2, star3, star4, star5;
    private FullProfileDTO profile;

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
        gridView = convertView;
        IReview item = ratingItems.get(position);
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.item_review, null);
        }
        findViews();


        Controller_User.getInstance().getFullProfile(item.getUserID(), fullProfileDTO -> {
            profile = fullProfileDTO;
            String friendName = item.getUsername();
            String reviewText = item.getReview();
            int itemRating = item.getRating();
            if (profile.getPictureURL() != null) {
                String profilePictureUrl = profile.getPictureURL();
                    Picasso.get().load(profilePictureUrl).into(profile_pic);
                    friend_name.setText(friendName);
                    review_text.setText(reviewText);
                    starfest(itemRating);
            } else {
                int defaultPic = R.drawable.icon_default_profilepicture;
                    Picasso.get().load(defaultPic).into(profile_pic);
                    friend_name.setText(friendName);
                    review_text.setText(reviewText);
                    starfest(itemRating);
            }
        });
        return gridView;
    }

    public void addItem(IReview review) {
        ratingItems.add(review);
        this.notifyDataSetChanged();
    }

    private void starfest(int rating) {
        if (rating == 0) {
            star1.setImageResource(R.drawable.icon_empty_star);
            star2.setImageResource(R.drawable.icon_empty_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        } else if (rating == 1) {
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_empty_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        } else if (rating == 2) {
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        } else if (rating == 3) {
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        } else if (rating == 4) {
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_filled_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        } else if (rating == 5) {
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_filled_star);
            star5.setImageResource(R.drawable.icon_filled_star);
        }
    }

    private void findViews() {
        profile_pic = gridView.findViewById(R.id.profile_pic);
        friend_name = gridView.findViewById(R.id.friend_name);
        review_text = gridView.findViewById(R.id.review_text);
        star1 = gridView.findViewById(R.id.ImageView_star_1);
        star2 = gridView.findViewById(R.id.ImageView_star_2);
        star3 = gridView.findViewById(R.id.ImageView_star_3);
        star4 = gridView.findViewById(R.id.ImageView_star_4);
        star5 = gridView.findViewById(R.id.ImageView_star_5);
    }
}