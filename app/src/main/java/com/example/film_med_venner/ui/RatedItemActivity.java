package com.example.film_med_venner.ui;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;


public class RatedItemActivity extends AppCompatActivity {
    private TextView description;
    Intent intent;
    private String review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_rated_item_description);
        intent = getIntent();
        review = intent.getStringExtra("review");
        description = findViewById(R.id.description);
        description.setText(review);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

}
