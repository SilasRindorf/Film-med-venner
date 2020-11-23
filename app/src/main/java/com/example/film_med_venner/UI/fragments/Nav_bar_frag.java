package com.example.film_med_venner.UI.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.film_med_venner.R;
import com.example.film_med_venner.UI.MainActivity;
import com.example.film_med_venner.UI.ProfileActivity;
import com.example.film_med_venner.UI.SearchActivity;
import com.example.film_med_venner.UI.SettingsActivity;

public class Nav_bar_frag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.frag_nav_bar, container, false);

        /**
         * Creating the onClickListener for home_btn and giving adding the intent for switching to another activity as well.
         */
        ImageButton btn = (ImageButton) view.findViewById(R.id.home_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Creating the onClickListener for profile_btn and giving adding the intent for switching to another activity as well.
         */
        btn = (ImageButton) view.findViewById(R.id.profile_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Creating the onClickListener for search_btn and giving adding the intent for switching to another activity as well.
         */
        btn = (ImageButton) view.findViewById(R.id.search_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Creating the onClickListener for settings_btn and giving adding the intent for switching to another activity as well.
         */
        btn = (ImageButton) view.findViewById(R.id.settings_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        /**
         * Returning the view in case none of the buttons former buttons were those pushed.
         */
        return view;
    }
}
