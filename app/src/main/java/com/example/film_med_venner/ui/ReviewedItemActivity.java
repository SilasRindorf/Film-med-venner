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


public class ReviewedItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_extended_review);
        Intent intent = getIntent();

        String review = intent.getStringExtra("reviewText");
        TextView reviewText = findViewById(R.id.review_text);
        reviewText.setText(review);

        int rating = intent.getIntExtra("starReview", 0);
        setStars(rating);

        String description = intent.getStringExtra("reviewDescription");
        TextView descriptionText = findViewById(R.id.description);
        descriptionText.setText(description);

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    private void setStars(int starReview) {
        ImageView star1 = findViewById(R.id.ImageView_star_1);
        ImageView star2 = findViewById(R.id.ImageView_star_2);
        ImageView star3 = findViewById(R.id.ImageView_star_3);
        ImageView star4 = findViewById(R.id.ImageView_star_4);
        ImageView star5 = findViewById(R.id.ImageView_star_5);
        star1.setImageResource(R.drawable.icon_empty_star);
        star2.setImageResource(R.drawable.icon_empty_star);
        star3.setImageResource(R.drawable.icon_empty_star);
        star4.setImageResource(R.drawable.icon_empty_star);
        star5.setImageResource(R.drawable.icon_empty_star);

        switch (starReview) {
            case  1:
                star1.setImageResource(R.drawable.icon_filled_star);
                break;
            case 2:
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                break;
            case 3:
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_filled_star);
                break;
            case 4:
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_filled_star);
                star4.setImageResource(R.drawable.icon_filled_star);
                break;
            case 5:
                star1.setImageResource(R.drawable.icon_filled_star);
                star2.setImageResource(R.drawable.icon_filled_star);
                star3.setImageResource(R.drawable.icon_filled_star);
                star4.setImageResource(R.drawable.icon_filled_star);
                star5.setImageResource(R.drawable.icon_filled_star);
                break;
        }
    }

}
