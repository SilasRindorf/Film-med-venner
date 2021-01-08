package com.example.film_med_venner.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Profile;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.ISearch;
import com.example.film_med_venner.ui.adapters.ReviewAdapter;

public class MovieDetailsActivity extends AppCompatActivity {
    GridView gridView;
    private ReviewAdapter reviewAdapter;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
    }
}