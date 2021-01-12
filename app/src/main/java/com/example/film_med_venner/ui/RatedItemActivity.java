package com.example.film_med_venner.ui;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;


public class RatedItemActivity extends AppCompatActivity {
    private TextView reviewText;
    private Intent intent;
    private String review;
    private int rating;
    private ImageView star1, star2, star3, star4, star5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_rated_item_description);
        intent = getIntent();
        review = intent.getStringExtra("reviewText");
        reviewText = findViewById(R.id.review_text);
        reviewText.setText(review);

        rating = intent.getIntExtra("starRating",0);
        setStars(rating);

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    private void setStars(int starRating){
        star1 = findViewById(R.id.ImageView_star_1);
        star2 = findViewById(R.id.ImageView_star_2);
        star3 = findViewById(R.id.ImageView_star_3);
        star4 = findViewById(R.id.ImageView_star_4);
        star5 = findViewById(R.id.ImageView_star_5);
        if (starRating == 0){
            star1.setImageResource(R.drawable.icon_empty_star);
            star2.setImageResource(R.drawable.icon_empty_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starRating == 1){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_empty_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starRating == 2){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starRating == 3){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starRating == 4){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_filled_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starRating == 5){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_filled_star);
            star5.setImageResource(R.drawable.icon_filled_star);
        }
    }

}
