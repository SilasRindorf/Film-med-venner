package com.example.film_med_venner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_MovieDetails;
import com.example.film_med_venner.controllers.Controller_Rating;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.ui.fragments.Write_review_frag;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    private GridView gridView;
    private Context ctx;
    private Controller_MovieDetails mdController = Controller_MovieDetails.getInstance();
    private Controller_Rating rController = Controller_Rating.getInstance();
    private Intent intent;

    private TextView title, plot, director, runtime, actors, yourReview;
    private ImageView moviePoster, star1, star2, star3, star4, star5;
    private ImageButton addToWatch, write_review_btn;

    private Movie movie;
    private Rating rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        intent = getIntent();

        movie = mdController.getMovie(intent.getStringExtra("Title"));

        try {
            rating = rController.getUserRating(Database.getInstance().getCurrentUser().getID(), movie.getImdbID());
        } catch (IDatabase.DatabaseException e) {
            e.printStackTrace();
        }

        write_review_btn = findViewById(R.id.image_btn_review);
        write_review_btn.setOnClickListener(this);

        moviePoster = findViewById(R.id.moviePoster);
        Picasso.get().load(movie.getPoster()).into(moviePoster);

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
        star1 = findViewById(R.id.ImageView_star_1);
        star2 = findViewById(R.id.ImageView_star_2);
        star3 = findViewById(R.id.ImageView_star_3);
        star4 = findViewById(R.id.ImageView_star_4);
        star5 = findViewById(R.id.ImageView_star_5);
        starFest();






        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);



    }
    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view == write_review_btn){
            Bundle bundle = new Bundle();
            bundle.putString("id", movie.getImdbID());
            Fragment review_frag = new Write_review_frag();
            review_frag.setArguments(bundle);
            addFrag(R.id.write_review_container, review_frag);
        }
    }

    private void starFest() {
        int stars;

        if (item.getRating() == 0){
            star1.setImageResource(R.drawable.icon_empty_star);
            star2.setImageResource(R.drawable.icon_empty_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (item.getRating() == 1){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_empty_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (item.getRating() == 2){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_empty_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (item.getRating() == 3){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_empty_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (item.getRating() == 4){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_filled_star);
            star5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (item.getRating() == 5){
            star1.setImageResource(R.drawable.icon_filled_star);
            star2.setImageResource(R.drawable.icon_filled_star);
            star3.setImageResource(R.drawable.icon_filled_star);
            star4.setImageResource(R.drawable.icon_filled_star);
            star5.setImageResource(R.drawable.icon_filled_star);
        }
    }

}