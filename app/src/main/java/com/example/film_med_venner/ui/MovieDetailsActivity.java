package com.example.film_med_venner.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private final Controller_MovieDetails mdController = Controller_MovieDetails.getInstance();
    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();
    private final List<IReview> reviewList = new ArrayList<>();
    private ListView gridView;
    private Context ctx;
    private TextView yourReview;
    private ImageView starFriend1, starFriend2, starFriend3, starFriend4, starFriend5;
    private ImageButton addToWatch, write_review_btn;
    private MovieDetailsAdapter movieDetailsAdapter;
    private Button showReviews_btn, leaveReviews_btn;
    //private LinearLayout friends_reviews_container;
    private Movie movie;
    private Review review;
    private int totalRating;
    private int raters;
    private int avgRating;
    private int adapterStatus = 0;
    private ScrollView scrollview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent intent = getIntent();
        ImageView[] stars = {findViewById(R.id.ImageView_star_1), findViewById(R.id.ImageView_star_2), findViewById(R.id.ImageView_star_3), findViewById(R.id.ImageView_star_4), findViewById(R.id.ImageView_star_5)};
        ImageView[] friendStars = {findViewById(R.id.ImageView_friend_star_1),findViewById(R.id.ImageView_friend_star_2),findViewById(R.id.ImageView_friend_star_3),findViewById(R.id.ImageView_friend_star_4),findViewById(R.id.ImageView_friend_star_5)};
        ctx = this;
        movieDetailsAdapter = new MovieDetailsAdapter(ctx, reviewList);
        movie = mdController.getMovie(intent.getStringExtra("Id"));
        findViews();

        bgThread.execute(() -> {

            try {
                Controller_Review.getInstance().getReview(Controller_User.getInstance().getCurrentUser().getID(), movie.getImdbID(), new RunnableReviewUI() {
                    @Override
                    public void run(IReview rating) {
                        review = (Review) rating;
                        uiThread.post(() -> {
                            if (review != null) {
                                starFest(stars,review.getRating());
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

        try {
            Controller_Review.getInstance().getFriendsWhoReviewed(movie.getImdbID(), string -> {
                try {
                    Controller_Review.getInstance().getReview(string, movie.getImdbID(), new RunnableReviewUI() {
                        @Override
                        public void run(IReview rating) {
                            Review r = (Review) rating;
                            System.out.println("FUCKINGLORTEREVIEW HVEM ER DU" + r);
                            totalRating += r.getRating();
                            raters++;
                            avgRating = totalRating / raters;
                            movieDetailsAdapter.addItem(r);
                            uiThread.post(() -> {
                                starFest(friendStars,avgRating);
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

    @Override
    public void onClick(View view) {
        if (view == write_review_btn) {
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
        } else if (view == addToWatch) {
            try {
                Controller_HomeFeed.getInstance().addToWatchListItem(new WatchItem(Controller_User.getInstance().getCurrentUser().getID(), movie.getImdbID()));
                Toast.makeText(MovieDetailsActivity.this, "Added movie to watch list", Toast.LENGTH_LONG).show();
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
                Toast.makeText(MovieDetailsActivity.this, "Failed to add movie to watch list", Toast.LENGTH_LONG).show();
            }
        } else if (view == showReviews_btn) {
            if (adapterStatus == 0) {
                gridView.setAdapter(movieDetailsAdapter);
                adapterStatus = 1;
            }
            scrollview.setVisibility(View.INVISIBLE);
            gridView.setVisibility(View.VISIBLE);
            leaveReviews_btn.setVisibility(View.VISIBLE);
        } else if (view == leaveReviews_btn) {
            scrollview.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
            leaveReviews_btn.setVisibility(View.INVISIBLE);
        }
    }

    public void starFest(ImageView[] stars, int starReview) {
        int starShape = R.drawable.icon_filled_star;
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
        starFriend1 = findViewById(R.id.ImageView_friend_star_1);
        starFriend2 = findViewById(R.id.ImageView_friend_star_2);
        starFriend3 = findViewById(R.id.ImageView_friend_star_3);
        starFriend4 = findViewById(R.id.ImageView_friend_star_4);
        starFriend5 = findViewById(R.id.ImageView_friend_star_5);
        gridView = findViewById(R.id.gridView);

        showReviews_btn = findViewById(R.id.show_reviews_btn);
        showReviews_btn.setOnClickListener(this);
        leaveReviews_btn = findViewById(R.id.leave_reviews_btn);
        leaveReviews_btn.setOnClickListener(this);
        write_review_btn = findViewById(R.id.image_btn_review);
        write_review_btn.setOnClickListener(this);

        addToWatch = findViewById(R.id.image_btn_add_to_watch_list);
        addToWatch.setOnClickListener(this);

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

    private void runLoadScreen(Context ctx, boolean keep) {
        Intent ld = new Intent(ctx, LoadingScreen.class);
        ld.putExtra("finished", keep);
        ld.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ld.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(ld);
    }

}