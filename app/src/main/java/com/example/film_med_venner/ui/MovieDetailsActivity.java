package com.example.film_med_venner.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.DTO.ReviewDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_HomeFeed;
import com.example.film_med_venner.controllers.Controller_MovieDetails;
import com.example.film_med_venner.controllers.Controller_Review;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.runnable.RunnableReviewsLoadUI;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.ui.fragments.Write_review_frag;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private GridView gridView;
    private Context ctx;
    private final Controller_MovieDetails mdController = Controller_MovieDetails.getInstance();
    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();

    private TextView yourReview;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private ImageView starFriend1, starFriend2, starFriend3, starFriend4, starFriend5;
    private ImageButton addToWatch, write_review_btn;

    private Movie movie;
    private Review rating;
    private int avgRating;
    private IReview[] reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent intent = getIntent();
        runLoadScreen(true);

        movie = mdController.getMovie(intent.getStringExtra("Id"));
        yourReview = findViewById(R.id.textView_your_review);
        star1 = findViewById(R.id.ImageView_star_1);
        star2 = findViewById(R.id.ImageView_star_2);
        star3 = findViewById(R.id.ImageView_star_3);
        star4 = findViewById(R.id.ImageView_star_4);
        star5 = findViewById(R.id.ImageView_star_5);
        starFriend1 = findViewById(R.id.ImageView_friend_star_1);
        starFriend2 = findViewById(R.id.ImageView_friend_star_2);
        starFriend3 = findViewById(R.id.ImageView_friend_star_3);
        starFriend4 = findViewById(R.id.ImageView_friend_star_4);
        starFriend5 = findViewById(R.id.ImageView_friend_star_5);

        bgThread.execute(() -> {

            try {
                Controller_Review.getInstance().getReview(Controller_User.getInstance().getCurrentUser().getID(), movie.getImdbID(), rating1 -> {
                    rating = (Review) rating1;
                    Log.e("uID: ", Controller_User.getInstance().getCurrentUser().getID());
                    Log.e("movID: ", movie.getImdbID());
                    uiThread.post(() -> {
                        if (rating != null) {
                            starFest(rating.getRating());
                            yourReview.setText(rating.getReview());
                        }

                    });
                    runLoadScreen(false);
                });

                Controller_Review.getInstance().getFriendReviews(new RunnableReviewsLoadUI() {
                    @Override
                    public void run() {
                        runLoadScreen(false);
                    }

                    @Override
                    public void run(IReview[] ratings) {
                        reviews = Controller_Review.getInstance().reviewDTOtoIReview((ReviewDTO[]) ratings);
                        int temp = 0;
                        for (IReview review : reviews) {
                            temp += review.getRating();
                        }
                        if (reviews.length != 0){
                            temp = temp / reviews.length;
                        }

                        avgRating = temp;
                        uiThread.post(() -> {
                            if (avgRating != 0) {
                                starFestFriends(avgRating);
                            }

                        });
                        runLoadScreen(false);
                    }
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }

        });


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
            if (rating != null){
                bundle.putBoolean("status",true);
                bundle.putInt("starReview",rating.getRating());
                bundle.putString("review",rating.getReview());

            }
            Fragment review_frag = new Write_review_frag();
            review_frag.setArguments(bundle);
            addFrag(R.id.write_review_container, review_frag);
        }
        if (view == addToWatch){
            try {
                Controller_HomeFeed.getInstance().addToWatchListItem(new WatchItem(Controller_User.getInstance().getCurrentUser().getID(), movie.getImdbID()));
                Toast.makeText(MovieDetailsActivity.this, "Added movie to watch list", Toast.LENGTH_LONG).show();
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
                Toast.makeText(MovieDetailsActivity.this, "Failed to add movie to watch list", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void starFest(int starReview) {
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

    private void starFestFriends(int starReview) {
        switch (starReview) {
            case 1:
                starFriend1.setImageResource(R.drawable.icon_filled_star);
                break;
            case 2:
                starFriend1.setImageResource(R.drawable.icon_filled_star);
                starFriend2.setImageResource(R.drawable.icon_filled_star);
                break;
            case 3:
                starFriend1.setImageResource(R.drawable.icon_filled_star);
                starFriend2.setImageResource(R.drawable.icon_filled_star);
                starFriend3.setImageResource(R.drawable.icon_filled_star);
                break;
            case 4:
                starFriend1.setImageResource(R.drawable.icon_filled_star);
                starFriend2.setImageResource(R.drawable.icon_filled_star);
                starFriend3.setImageResource(R.drawable.icon_filled_star);
                starFriend4.setImageResource(R.drawable.icon_filled_star);
                break;
            case 5:
                starFriend1.setImageResource(R.drawable.icon_filled_star);
                starFriend2.setImageResource(R.drawable.icon_filled_star);
                starFriend3.setImageResource(R.drawable.icon_filled_star);
                starFriend4.setImageResource(R.drawable.icon_filled_star);
                starFriend5.setImageResource(R.drawable.icon_filled_star);
                break;
        }
    }

    private void runLoadScreen(boolean keep) {
        Intent ld = new Intent(MovieDetailsActivity.this, LoadingScreen.class);
        ld.putExtra("finished", keep);
        ld.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ld.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(ld);
    }

}