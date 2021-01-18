package com.example.film_med_venner.ui.profileActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.ui.login.MainActivity;

public class SettingsFacebookUserActivity extends AppCompatActivity implements View.OnClickListener {
    private Button change_profile_picture_btn, save_changes_btn, log_out_btn;
    private EditText profile_top_genre_edit_text;
    private TextView profile_name_textView, profile_mail_textView;
    private ImageView profile_picture;
    //TODO Switches i settings?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_facebook_user);
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
        if (view == log_out_btn) {
            try {
                Controller_User.getInstance().logOut(() -> {
                    Intent intent = new Intent(/*org class*/this, /*Log In Screen*/MainActivity.class);
                    startActivity(intent);
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        } else if (view == save_changes_btn) {
            try {
                Controller_User.getInstance().updateUser(profile_name_textView.getText().toString(), profile_mail_textView.getText().toString(), profile_top_genre_edit_text.getText().toString(), new RunnableErrorUI() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingsFacebookUserActivity.this, "Settings have been updated", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void handleError(IDatabase.DatabaseException e) {
                        Toast.makeText(SettingsFacebookUserActivity.this, "An error has occured", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (IDatabase.DatabaseException e) {
                Log.e("Error", "Error in updating user");
                e.printStackTrace();

            }

        }
    }

    public void findViews(){
        // BUTTONS
        change_profile_picture_btn = findViewById(R.id.change_profile_picture);
        change_profile_picture_btn.setOnClickListener(this);
        save_changes_btn = findViewById(R.id.save_changes_btn);
        save_changes_btn.setOnClickListener(this);
        log_out_btn = findViewById(R.id.btn_log_out);
        log_out_btn.setOnClickListener(this);
        // EDITTEXT
        profile_name_textView = findViewById(R.id.profile_name);
        profile_mail_textView = findViewById(R.id.profile_mail);
        profile_top_genre_edit_text = findViewById(R.id.profile_top_genre);
        // IMAGEVIEW
        profile_picture = findViewById(R.id.profile_picture);
    }
}
