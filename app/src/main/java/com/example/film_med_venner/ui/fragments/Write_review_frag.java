package com.example.film_med_venner.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.DAO.WatchItem;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_HomeFeed;
import com.example.film_med_venner.controllers.Controller_Review;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.ui.MovieDetailsActivity;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class Write_review_frag extends DialogFragment {
    private ImageView yourStar1, yourStar2, yourStar3, yourStar4, yourStar5;
    private int starReview;
    private String movieID;
    private EditText reviewInput;
    private Boolean status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.frag_write_review, container, false);

        ImageView[] stars = {view.findViewById(R.id.ImageView_star_1), view.findViewById(R.id.ImageView_star_2), view.findViewById(R.id.ImageView_star_3), view.findViewById(R.id.ImageView_star_4), view.findViewById(R.id.ImageView_star_5)};

        // Getting arguments from MovieDetailsActivity in the form of a "Bundle"
        movieID = getArguments().getString("id");
        starReview = getArguments().getInt("starReview");
        String review = getArguments().getString("review");
        status = getArguments().getBoolean("status");
        for (int i = 0; i < 5; i++) {
            final int cur = i + 1;
            stars[i].setOnClickListener(v -> {
                starReview = cur;
                starFest(stars, cur);
            });

        }


        //Creating the onClickListener for cancel_review_btn and giving adding the intent for switching to another activity as well.
        Button btn = (Button) view.findViewById(R.id.cancel_review_btn);
        btn.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Review cancelled", Toast.LENGTH_LONG).show();
            closefragment();
        });

        //Creating the onClickListener for submit_review_btn and giving adding the intent for switching to another activity as well.
        reviewInput = view.findViewById(R.id.review_input_editText);
        btn = (Button) view.findViewById(R.id.submit_review_btn);
        btn.setOnClickListener(v -> {
            Thread newThread = new Thread(() -> {
                if (starReview > 1 && starReview < 6) {
                    IReview newReview = new Review(starReview, Controller_User.getInstance().getCurrentUser().getName(), movieID, reviewInput.getText().toString(), Controller_User.getInstance().getCurrentUser().getID());
                    if (status) {
                        try {
                            Controller_Review.getInstance().updateReview(newReview);
                            //writeToast("Review submitted");
                            ((MovieDetailsActivity) getActivity()).setReview(newReview);
                            closefragment();
                        } catch (IDatabase.DatabaseException e) {
                            e.printStackTrace();
                            //writeToast("Failed to update review");
                        }
                    } else {
                        try {
                            Controller_Review.getInstance().createReview(newReview);
                            Controller_HomeFeed.getInstance().removeToWatchListItem(movieID);
                            Controller_HomeFeed.getInstance().addWatchedListItem(new WatchItem(Controller_User.getInstance().getCurrentUser().getName(), movieID));
                        } catch (IDatabase.DatabaseException e) {
                            e.printStackTrace();
                            //writeToast("Failed to create review");
                        }
                        //writeToast("Review submitted");
                        ((MovieDetailsActivity) getActivity()).setReview(newReview);
                        closefragment();
                    }
                } else {
                    //Toast.makeText(getActivity(), "No rating given", Toast.LENGTH_LONG).show();
                }
            });
            newThread.start();
        });


        // Setting the review text and star rating to the previously given review and star rating

        reviewInput.setText(review);

        //Returning the view in case none of the buttons former buttons were those pushed.

        return view;
    }

    private void writeToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG);
    }

    private void closefragment() {
        getFragmentManager().beginTransaction().remove(Write_review_frag.this).commit();

    }

    public void starFest(ImageView[] stars, int starReview) {
        int starShape = R.drawable.icon_filled_star;
        for (int i = 0; i < 5; i++) {
            Picasso.get().load(R.drawable.icon_empty_star).into(stars[i]);
        }
        //On purpose no break;
        switch (starReview) {
            case 5:
                Picasso.get().load(R.drawable.icon_filled_star).into(stars[4]);
                stars[4].setImageResource(starShape);
            case 4:
                Picasso.get().load(R.drawable.icon_filled_star).into(stars[3]);
                stars[3].setImageResource(starShape);
            case 3:
                Picasso.get().load(R.drawable.icon_filled_star).into(stars[2]);
                stars[2].setImageResource(starShape);
            case 2:
                Picasso.get().load(R.drawable.icon_filled_star).into(stars[1]);
                stars[1].setImageResource(starShape);
            case 1:
                Picasso.get().load(R.drawable.icon_filled_star).into(stars[0]);
                stars[0].setImageResource(starShape);
        }
    }
}