package com.example.film_med_venner.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.film_med_venner.R;
import com.example.film_med_venner.activities.ChatActivity;
import com.example.film_med_venner.activities.MainActivity;

public class Nav_bar_frag extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.frag_nav_bar, container, false);
        int[] btnIDs = {R.id.home_btn};
        ImageButton btn;
        for (int btnID : btnIDs) {
            btn = (ImageButton) view.findViewById(btnID);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MainActivity.class);
                }
            });

        }


        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
