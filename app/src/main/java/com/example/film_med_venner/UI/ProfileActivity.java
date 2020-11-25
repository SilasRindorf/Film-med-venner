package com.example.film_med_venner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.film_med_venner.R;
import com.example.film_med_venner.UI.fragments.Nav_bar_frag;
import com.example.film_med_venner.UI.profileActivities.RatingActivity;
import com.example.film_med_venner.UI.profileActivities.ReviewActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout l_layout_review;
    LinearLayout l_layout_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        l_layout_review = findViewById(R.id.linearLayout_review);
        l_layout_review.setOnClickListener(this);
        l_layout_rating = findViewById(R.id.linearLayout_rating);
        l_layout_rating.setOnClickListener(this);
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view == l_layout_review){
            setContentView(R.layout.activity_review);
            Intent intent = new Intent(this, ReviewActivity.class);
            startActivity(intent);
        }
        else if (view == l_layout_rating){
            setContentView(R.layout.activity_rating);
            Intent intent = new Intent(this, RatingActivity.class);
            startActivity(intent);
        }
    }
}
