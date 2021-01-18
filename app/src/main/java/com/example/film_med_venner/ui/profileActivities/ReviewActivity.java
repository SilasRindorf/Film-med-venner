package com.example.film_med_venner.ui.profileActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Review;
import com.example.film_med_venner.ui.adapters.ReviewAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.controllers.Controller_Friends;
import com.example.film_med_venner.interfaces.IController.IProfileController;
import com.example.film_med_venner.interfaces.IReview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    GridView gridView;
    private ReviewAdapter reviewAdapter;
    private Context ctx;
    IProfileController controller = Controller_Friends.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        gridView = findViewById(R.id.gridView);
        //TODO Review Activity not working
        /*bgThread.execute(() -> {
            try {
                List<IReview> items = new ArrayList<>();
                controller.getReviews((RunnableReviewsUI) -> {
                    items = Arrays.asList(Controller_Review.getInstance().getReviewItems());
                    uiThread.post(() -> {
                       reviewAdapter = new ReviewAdapter(ctx, items);
                gridView.setAdapter(reviewAdapter);
                gridView.setVisibility(View.VISIBLE);
                    });
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        });*/

    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }


}