package com.example.film_med_venner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.ui.settingActivities.HomefeedSettingActivity;
import com.example.film_med_venner.ui.settingActivities.ProfileSettingActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView profSet, homeSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);


        profSet = findViewById(R.id.text_profile_settings);
        profSet.setOnClickListener(this);
        homeSet = findViewById(R.id.text_homefeed_settings);
        homeSet.setOnClickListener(this);
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view == profSet) {
            Intent intent = new Intent(this, ProfileSettingActivity.class);
            startActivity(intent);
        } else if (view == homeSet) {
            Intent intent = new Intent(this, HomefeedSettingActivity.class);
            startActivity(intent);
        }


    }
}
