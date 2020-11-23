package com.example.film_med_venner.fragments;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.film_med_venner.R;
import com.example.film_med_venner.activities.MainActivity;

public class Nav_bar_frag extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.frag_nav_bar, container, false);
        ImageButton btn = (ImageButton) view.findViewById(R.id.home_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),MainActivity.class);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
