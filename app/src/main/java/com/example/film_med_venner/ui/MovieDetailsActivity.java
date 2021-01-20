package com.example.film_med_venner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_HomeFeed;
import com.example.film_med_venner.controllers.Controller_MovieDetails;
import com.example.film_med_venner.controllers.Controller_Review;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewUI;
import com.example.film_med_venner.ui.adapters.MovieDetailsAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.ui.fragments.Write_review_frag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String TAG = "MovieDetailsActivity";
    private final Controller_MovieDetails mdController = Controller_MovieDetails.getInstance();
    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();
    private final List<IReview> reviewList = new ArrayList<>();
    private ListView gridView;
    private TextView yourReview;
    private MovieDetailsAdapter movieDetailsAdapter;
    private Movie movie;
    private Review review;
    private int totalRating;
    private int raters;
    private int avgRating;
    private int adapterStatus = 0;
    private ScrollView scrollview;
    private ImageView[] stars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        stars =  new ImageView[5];
        stars[0] = findViewById(R.id.ImageView_star_1);
        stars[1] = findViewById(R.id.ImageView_star_2);
        stars[2] = findViewById(R.id.ImageView_star_3);
        stars[3] = findViewById(R.id.ImageView_star_4);
        stars[4] = findViewById(R.id.ImageView_star_5);

        ImageView[] friendStars = {findViewById(R.id.ImageView_friend_star_1), findViewById(R.id.ImageView_friend_star_2), findViewById(R.id.ImageView_friend_star_3), findViewById(R.id.ImageView_friend_star_4), findViewById(R.id.ImageView_friend_star_5)};
        movieDetailsAdapter = new MovieDetailsAdapter(this, reviewList);
        movie = mdController.getMovie(intent.getStringExtra("Id"));
        findViews();
        getCurrentUsersReview();


        try {
            Controller_Review.getInstance().getFriendsWhoReviewed(movie.getImdbID(), string -> {
                try {
                    Controller_Review.getInstance().getReview(string, movie.getImdbID(), new RunnableReviewUI() {
                        @Override
                        public void run(IReview rating) {
                            Review r = (Review) rating;
                            totalRating += r.getRating();
                            raters++;
                            avgRating = totalRating / raters;
                            movieDetailsAdapter.addItem(r);
                            uiThread.post(() -> {
                                starFest(friendStars, avgRating);
                            });
                        }

                        @Override
                        public void run() {
                        }
                    });
                } catch (IDatabase.DatabaseException e) {
                    e.printStackTrace();
                }

            });
        } catch (IDatabase.DatabaseException e) {
            e.printStackTrace();
        }
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }


    public void starFest(ImageView[] stars, int starReview) {
        int starShape = R.drawable.icon_filled_star;
        //On purpose no break;
        switch (starReview) {
            case 5:
                stars[4].setImageResource(starShape);
            case 4:
                stars[3].setImageResource(starShape);
            case 3:
                stars[2].setImageResource(starShape);
            case 2:
                stars[1].setImageResource(starShape);
            case 1:
                stars[0].setImageResource(starShape);
        }
    }

    private void findViews() {
        yourReview = findViewById(R.id.textView_your_review);
        gridView = findViewById(R.id.gridView);

        findViewById(R.id.show_reviews_btn).setOnClickListener(view -> {
            if (adapterStatus == 0) {
                gridView.setAdapter(movieDetailsAdapter);
                adapterStatus = 1;
            }
            scrollview.setVisibility(View.INVISIBLE);
            gridView.setVisibility(View.VISIBLE);
            findViewById(R.id.leave_reviews_btn).setVisibility(View.VISIBLE);

        });

        findViewById(R.id.leave_reviews_btn).setOnClickListener(view -> {
            scrollview.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
            findViewById(R.id.leave_reviews_btn).setVisibility(View.INVISIBLE);
        });

        findViewById(R.id.image_btn_review).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", movie.getImdbID());
            if (review != null) {
                bundle.putBoolean("status", true);
                bundle.putInt("starReview", review.getRating());
                bundle.putString("review", review.getReview());
            }
            Fragment review_frag = new Write_review_frag();
            review_frag.setArguments(bundle);
            addFrag(R.id.write_review_container, review_frag);

        });
        findViewById(R.id.image_btn_add_to_watch_list).setOnClickListener(view -> {
            try {
                Controller_HomeFeed.getInstance().addToWatchListItem(new WatchItem(Controller_User.getInstance().getCurrentUser().getID(), movie.getImdbID()));
                Toast.makeText(MovieDetailsActivity.this, "Added movie to watch list", Toast.LENGTH_LONG).show();
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
                Toast.makeText(MovieDetailsActivity.this, "Failed to add movie to watch list", Toast.LENGTH_LONG).show();
            }
        });

        ImageView moviePoster = findViewById(R.id.moviePoster);
        Picasso.get().load(movie.getPoster()).into(moviePoster);

        TextView title = findViewById(R.id.textView_title);
        title.setText(movie.getTitle());
        TextView plot = findViewById(R.id.textView_plot);
        plot.setText(movie.getPlot());
        TextView director = findViewById(R.id.textView_director);
        director.setText(movie.getDirector());
        TextView runtime = findViewById(R.id.textView_runtime);
        runtime.setText(movie.getRuntime());
        TextView actors = findViewById(R.id.textView_actors);
        actors.setText(movie.getActors());

        scrollview = findViewById(R.id.scrollView);
    }

    public void getCurrentUsersReview(){
        bgThread.execute(() -> {
            try {
                Controller_Review.getInstance().getReview(Controller_User.getInstance().getCurrentUser().getID(), movie.getImdbID(), new RunnableReviewUI() {
                    @Override
                    public void run(IReview rating) {
                        review = (Review) rating;
                        uiThread.post(() -> {
                            if (review != null) {
                                starFest(stars, review.getRating());
                                yourReview.setText(review.getReview());
                            }
                        });
                    }

                    @Override
                    public void run() {
                    }
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });
    }


    public void setReview(IReview review){
        Log.e(TAG,"I was called");
        this.review = (Review) review;
        uiThread.post(() -> {
            if (review != null) {
                starFest(stars, review.getRating());
                yourReview.setText(review.getReview());
            }
        });    }



}