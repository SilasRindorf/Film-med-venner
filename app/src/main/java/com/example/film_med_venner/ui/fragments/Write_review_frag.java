package com.example.film_med_venner.ui.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.film_med_venner.DAO.Review;
import com.example.film_med_venner.R;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IReview;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class Write_review_frag extends DialogFragment {
    private ImageView yourStar1, yourStar2, yourStar3, yourStar4, yourStar5;
    private int starReview;
    private String movieID;
    private String review;
    private EditText reviewInput;
    private Boolean status;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.frag_write_review,container,false);
        System.out.println(getArguments().getString("review"));
        /**
         * Getting arguments from MovieDetailsActivity in the form of a "Bundle"
         */
        movieID = getArguments().getString("id");
        starReview = getArguments().getInt("starReview");
        review = getArguments().getString("review");
        status = getArguments().getBoolean("status");

        /**
         * Creating the onClickListener for ImageView_star_1 and giving a rating
         */
        yourStar1 = view.findViewById(R.id.ImageView_star_1);
        yourStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starReview = 1;
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar1);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar2);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar3);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar4);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar5);
            }
        });
        /**
         * Creating the onClickListener for ImageView_star_2 and giving a rating
         */
        yourStar2 = view.findViewById(R.id.ImageView_star_2);
        yourStar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starReview = 2;
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar1);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar2);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar3);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar4);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar5);
            }
        });
        /**
         * Creating the onClickListener for ImageView_star_3 and giving a rating
         */
        yourStar3 = view.findViewById(R.id.ImageView_star_3);
        yourStar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starReview = 3;
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar1);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar2);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar3);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar4);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar5);
            }
        });
        /**
         * Creating the onClickListener for ImageView_star_4 and giving a rating
         */
        yourStar4 = view.findViewById(R.id.ImageView_star_4);
        yourStar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starReview = 4;
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar1);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar2);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar3);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar4);
                Picasso.get().load(R.drawable.icon_empty_star).into(yourStar5);
            }
        });
        /**
         * Creating the onClickListener for ImageView_star_5 and giving a rating
         */
        yourStar5 = view.findViewById(R.id.ImageView_star_5);
        yourStar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starReview = 5;
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar1);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar2);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar3);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar4);
                Picasso.get().load(R.drawable.icon_filled_star).into(yourStar5);
            }
        });


        /**
         * Creating the onClickListener for cancel_review_btn and giving adding the intent for switching to another activity as well.
         */
        Button btn = (Button) view.findViewById(R.id.cancel_review_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Review cancelled", Toast.LENGTH_LONG).show();
                closefragment();
            }
        });

        /**
         * Creating the onClickListener for submit_review_btn and giving adding the intent for switching to another activity as well.
         */
        reviewInput = view.findViewById(R.id.review_input_editText);
        btn = (Button) view.findViewById(R.id.submit_review_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (starReview == 1 || starReview == 2 || starReview == 3 || starReview == 4 || starReview == 5){
                    IReview newReview = new Review(starReview, Database.getInstance().getCurrentUser().getName(), movieID, reviewInput.getText().toString(),Database.getInstance().getCurrentUser().getID());
                    if (status == true){
                        try {
                            Database.getInstance().updateReviews(newReview);
                            Toast.makeText(getActivity(), "Review submitted", Toast.LENGTH_LONG).show();
                        } catch (IDatabase.DatabaseException e){
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Failed to update review", Toast.LENGTH_LONG).show();
                        }
                    } else {
                    try {
                        Database.getInstance().createReview(newReview);
                    } catch (IDatabase.DatabaseException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Failed to create review", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getActivity(), "Review submitted", Toast.LENGTH_LONG).show();
                    closefragment();
                    }
                } else {
                    Toast.makeText(getActivity(), "No rating given", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * Setting the review text and star rating to the previously given review and star rating
         */
        starFest(starReview);
        reviewInput.setText(review);

        /**
         * Returning the view in case none of the buttons former buttons were those pushed.
         */
        return view;
    }
    private void closefragment() {
        getFragmentManager().beginTransaction().remove(Write_review_frag.this).commit();

    }

    private void starFest(int starReview) {
        if (starReview == 0){
            yourStar1.setImageResource(R.drawable.icon_empty_star);
            yourStar2.setImageResource(R.drawable.icon_empty_star);
            yourStar3.setImageResource(R.drawable.icon_empty_star);
            yourStar4.setImageResource(R.drawable.icon_empty_star);
            yourStar5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starReview == 1){
            yourStar1.setImageResource(R.drawable.icon_filled_star);
            yourStar2.setImageResource(R.drawable.icon_empty_star);
            yourStar3.setImageResource(R.drawable.icon_empty_star);
            yourStar4.setImageResource(R.drawable.icon_empty_star);
            yourStar5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starReview == 2){
            yourStar1.setImageResource(R.drawable.icon_filled_star);
            yourStar2.setImageResource(R.drawable.icon_filled_star);
            yourStar3.setImageResource(R.drawable.icon_empty_star);
            yourStar4.setImageResource(R.drawable.icon_empty_star);
            yourStar5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starReview == 3){
            yourStar1.setImageResource(R.drawable.icon_filled_star);
            yourStar2.setImageResource(R.drawable.icon_filled_star);
            yourStar3.setImageResource(R.drawable.icon_filled_star);
            yourStar4.setImageResource(R.drawable.icon_empty_star);
            yourStar5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starReview == 4){
            yourStar1.setImageResource(R.drawable.icon_filled_star);
            yourStar2.setImageResource(R.drawable.icon_filled_star);
            yourStar3.setImageResource(R.drawable.icon_filled_star);
            yourStar4.setImageResource(R.drawable.icon_filled_star);
            yourStar5.setImageResource(R.drawable.icon_empty_star);
        }
        else if (starReview == 5){
            yourStar1.setImageResource(R.drawable.icon_filled_star);
            yourStar2.setImageResource(R.drawable.icon_filled_star);
            yourStar3.setImageResource(R.drawable.icon_filled_star);
            yourStar4.setImageResource(R.drawable.icon_filled_star);
            yourStar5.setImageResource(R.drawable.icon_filled_star);
        }
    }


}