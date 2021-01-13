package com.example.film_med_venner.ui.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.film_med_venner.R;
import com.example.film_med_venner.ui.HomeActivity;
import com.example.film_med_venner.ui.ProfileActivity;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class Write_review_frag extends DialogFragment {
    private ImageView yourStar1, yourStar2, yourStar3, yourStar4, yourStar5;
    private int rating;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.frag_write_review,container,false);
        /**
         * Creating the onClickListener for ImageView_star_1 and giving a rating
         */
        yourStar1 = view.findViewById(R.id.ImageView_star_1);
        yourStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = 1;
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
                rating = 2;
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
                rating = 3;
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
                rating = 4;
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
                rating = 5;
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
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setMessage("You just canceled your review. Good job!");
                alertDialog.show();
                closefragment();
            }
        });

        /**
         * Creating the onClickListener for submit_review_btn and giving adding the intent for switching to another activity as well.
         */
        btn = (Button) view.findViewById(R.id.submit_review_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("The submit button was clicked.");
                if (rating == 1 || rating == 2 || rating == 3 || rating == 4 || rating == 5){
                    //TODO the method below is the review text to be sent to backend.
                /* EditText reviewInput = (EditText) view.findViewById(R.id.review_input_editText);
                reviewInput.getText(); */
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("You just submitted your review. Good job!");
                    alertDialog.show();
                    closefragment();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("You haven't given any rating yet. Please do that before submitting");
                    alertDialog.show();
                }
            }
        });
        /**
         * Returning the view in case none of the buttons former buttons were those pushed.
         */
        return view;
    }
    private void closefragment() {
        getFragmentManager().beginTransaction().remove(Write_review_frag.this).commit();

    }

}