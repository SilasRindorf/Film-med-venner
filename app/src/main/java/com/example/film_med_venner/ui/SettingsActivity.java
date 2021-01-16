package com.example.film_med_venner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button change_profile_picture_btn, save_password_btn, save_changes_btn, log_out_btn;
    private EditText profile_name_edit_text, profile_phone_edit_text, profile_mail_edit_text, profile_top_genre_edit_text, profile_password_edit_text, profile_new_password_edit_text, profile_repeat_new_password_edit_text;
    private ImageView profile_picture;
    //TODO Switches i settings?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);
        findViews();
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view == log_out_btn){
            Database.getInstance().logOut(()->{
                Intent intent = new Intent(/*org class*/this, /*Log In Screen*/MainActivity.class);
                startActivity(intent);
            });
        }
    }

    public void findViews(){
        // BUTTONS
        change_profile_picture_btn = findViewById(R.id.change_profile_picture);
        change_profile_picture_btn.setOnClickListener(this);
        save_password_btn = findViewById(R.id.save_password_btn);
        save_password_btn.setOnClickListener(this);
        save_changes_btn = findViewById(R.id.save_changes_btn);
        save_changes_btn.setOnClickListener(this);
        log_out_btn = findViewById(R.id.btn_log_out);
        log_out_btn.setOnClickListener(this);
        // EDITTEXT
        profile_name_edit_text = findViewById(R.id.profile_name);
        profile_phone_edit_text = findViewById(R.id.profile_phone);
        profile_mail_edit_text = findViewById(R.id.profile_mail);
        profile_top_genre_edit_text = findViewById(R.id.profile_top_genre);
        profile_password_edit_text = findViewById(R.id.profile_password);
        profile_new_password_edit_text = findViewById(R.id.profile_new_password);
        profile_repeat_new_password_edit_text = findViewById(R.id.profile_repeat_new_password);
        // IMAGEVIEW
        profile_picture = findViewById(R.id.profile_picture);
    }
}
