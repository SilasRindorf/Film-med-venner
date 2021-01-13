package com.example.film_med_venner.ui.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.film_med_venner.R;
import com.example.film_med_venner.ui.HomeActivity;
import com.example.film_med_venner.ui.ProfileActivity;

import javax.annotation.Nullable;

public class Write_review_frag extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.frag_write_review,container,false);

        /**
         * Creating the onClickListener for cancel_review_btn and giving adding the intent for switching to another activity as well.
         */
        Button btn = (Button) view.findViewById(R.id.cancel_review_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("You just canceled your review. Good job!");
            }
        });

        /**
         * Creating the onClickListener for submit_review_btn and giving adding the intent for switching to another activity as well.
         */
        btn = (Button) view.findViewById(R.id.submit_review_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("You just submitted your review. Good job!");
            }
        });
        /**
         * Returning the view in case none of the buttons former buttons were those pushed.
         */
        return view;
    }

}