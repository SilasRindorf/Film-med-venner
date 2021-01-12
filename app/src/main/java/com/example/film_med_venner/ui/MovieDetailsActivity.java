package com.example.film_med_venner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_MovieDetails;
import com.example.film_med_venner.ui.adapters.ReviewAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {


    GridView gridView;
    private ReviewAdapter reviewAdapter;
    private Context ctx;
    Controller_MovieDetails controller = Controller_MovieDetails.getInstance();
    Intent intent;


    TextView title, plot, director, runtime, actors, yourReview;
    ImageView moviePoster, yourStar1, yourStar2, yourStar3, yourStar4, yourStar5,
              friendStar1, friendStar2, friendStar3, friendStar4, friendStar5;
    ImageButton addToWatch, review, rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        intent = getIntent();

        Movie movie = controller.getMovie(intent.getStringExtra("Title"));

        title = findViewById(R.id.textView_title);
        title.setText(movie.getTitle());
        plot = findViewById(R.id.textView_plot);
        plot.setText(movie.getPlot());
        director = findViewById(R.id.textView_director);
        director.setText(movie.getDirector());
        runtime = findViewById(R.id.textView_runtime);
        runtime.setText(movie.getRuntime());
        actors = findViewById(R.id.textView_actors);
        actors.setText(movie.getActors());
        yourReview = findViewById(R.id.textView_your_review);

        moviePoster = findViewById(R.id.moviePoster);
        Picasso.get().load(movie.getPoster()).into(moviePoster);




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