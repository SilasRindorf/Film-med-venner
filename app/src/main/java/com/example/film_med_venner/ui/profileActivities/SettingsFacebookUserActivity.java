package com.example.film_med_venner.ui.profileActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.ui.loginActivities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.sentry.Sentry;

public class SettingsFacebookUserActivity extends AppCompatActivity implements View.OnClickListener {
    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();
    private Button change_profile_picture_btn, save_changes_btn, log_out_btn ;
    private EditText profile_top_genre_edit_text;
    private TextView profile_name_textView, profile_mail_textView;
    private ImageView profile_picture;
    private String userID, profile_picture_url, profile_name, profile_email, profile_mvgPref;
    private FullProfileDTO profile;
    private Switch switch_lists;

    //TODO Switches i settings?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_facebook_user);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);

        findViews();
        userID = Controller_User.getInstance().getCurrentUser().getID();

        bgThread.execute(() -> {
            Controller_User.getInstance().getFullProfile(userID, RunnableFullProfileUI -> {
                profile = RunnableFullProfileUI;
                profile_picture_url = profile.getPictureURL();
                profile_name = profile.getName();
                profile_email = Controller_User.getInstance().getCurrentUserEmail();
                profile_mvgPref = profile.getmvGPrefs();
                uiThread.post(() -> {
                    if (profile_picture_url != null) {
                        //TODO Giv billedet runde kanter
                        Picasso.get().load(profile_picture_url).into(profile_picture);
                        profile_name_textView.setText(profile_name);
                        profile_mail_textView.setText(profile_email);
                        profile_top_genre_edit_text.setText(profile_mvgPref);
                    }
                });
            });
        });
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
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(intent, 0);
                });
            } catch (IDatabase.DatabaseException e) {
                Sentry.captureMessage("SettingsFacebookUserActivity->log_out_btn(uId:" + Controller_User.getInstance().getCurrentUser().getID() + ")" + ":  " + e.toString());
            }
        } else if (view == change_profile_picture_btn) {
            Toast.makeText(SettingsFacebookUserActivity.this, "Unfortunately this feature has not been implemented yet.", Toast.LENGTH_LONG).show();
        } else if (view == save_changes_btn) {
            try {
                Controller_User.getInstance().updateUser(profile_name_textView.getText().toString(), profile_mail_textView.getText().toString(), profile_top_genre_edit_text.getText().toString(), new RunnableErrorUI() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingsFacebookUserActivity.this, "Settings have been updated", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void handleError(IDatabase.DatabaseException e) {
                        Sentry.captureMessage("SettingsFacebookUserActivity->change_profile_picture_btn->updateUser(uId:" + Controller_User.getInstance().getCurrentUser().getID() + ")" + ":  " + e.toString());
                        Toast.makeText(SettingsFacebookUserActivity.this, "An error has occured", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (IDatabase.DatabaseException e) {
                Sentry.captureMessage("SettingsFacebookUserActivity->updateUserPassword(uId:" + Controller_User.getInstance().getCurrentUser().getID() + ")" + ":  " + e.toString());
            }

        }
    }

    public void findViews() {
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
        // TEXTVIEW
        //profile_id = findViewById(R.id.profile_id);
        // SWITCHES
        switch_lists = findViewById(R.id.switch_lists);
        switch_lists.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SettingsFacebookUserActivity.this, "Unfortunately this feature has not been implemented yet.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SettingsFacebookUserActivity.this, "Unfortunately this feature has not been implemented yet.", Toast.LENGTH_LONG).show();
            }
        });
        Switch switch_reviews = findViewById(R.id.switch_reviews);
        switch_reviews.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(SettingsFacebookUserActivity.this, "Unfortunately this feature has not been implemented yet.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SettingsFacebookUserActivity.this, "Unfortunately this feature has not been implemented yet.", Toast.LENGTH_LONG).show();
            }
        });
        // CHECKBOX
        CheckBox checkBox_mark_all = findViewById(R.id.checkBox_mark_all);
        checkBox_mark_all.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                Toast.makeText(SettingsFacebookUserActivity.this, "Unfortunately this feature has not been implemented yet.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SettingsFacebookUserActivity.this, "Unfortunately this feature has not been implemented yet.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
