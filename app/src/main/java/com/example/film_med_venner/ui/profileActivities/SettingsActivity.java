package com.example.film_med_venner.ui.profileActivities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.ui.login.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button change_profile_picture_btn, save_password_btn, save_changes_btn, log_out_btn, copy_id_btn;
    private EditText profile_name_edit_text, profile_phone_edit_text, profile_mail_edit_text, profile_top_genre_edit_text, profile_password_edit_text, profile_new_password_edit_text, profile_repeat_new_password_edit_text;
    private ImageView profile_picture;
    private FullProfileDTO profile;
    private final Executor bgThread = Executors.newSingleThreadExecutor();
    private final Handler uiThread = new Handler();
    private String userID, profile_picture_url, profile_name, profile_email, profile_mvgPref;
    private TextView profile_id;
    private Context ctx;

    //TODO Switches i settings?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container, frag);
        ctx = this;
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
                        profile_name_edit_text.setText(profile_name, TextView.BufferType.EDITABLE);
                        profile_mail_edit_text.setText(profile_email, TextView.BufferType.EDITABLE);
                        profile_top_genre_edit_text.setText(profile_mvgPref, TextView.BufferType.EDITABLE);
                        profile_id.setText(userID);
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
                e.printStackTrace();
            }
        } else if (view == save_changes_btn) {

            try {
                Controller_User.getInstance().updateUser(profile_name_edit_text.getText().toString(), profile_mail_edit_text.getText().toString(), profile_top_genre_edit_text.getText().toString(), new RunnableErrorUI() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingsActivity.this, "Settings have been updated", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void handleError(IDatabase.DatabaseException e) {
                        Toast.makeText(SettingsActivity.this, "An error has occured", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (IDatabase.DatabaseException e) {
                Log.e("Error", "Error in updating user");
                e.printStackTrace();

            }

        } else if (view == save_password_btn) {
            if (profile_new_password_edit_text.getText().toString().equals(profile_repeat_new_password_edit_text.getText().toString())) {
                Controller_User.getInstance().updateUserPassword(profile_password_edit_text.getText().toString(), profile_new_password_edit_text.getText().toString(), new RunnableErrorUI() {
                    @Override
                    public void run() {
                        Toast.makeText(SettingsActivity.this, "Password has been updated", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void handleError(IDatabase.DatabaseException e) {
                        Toast.makeText(SettingsActivity.this, "Something went wrong, check passwords and try again", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(SettingsActivity.this, "The new passwords need to be the same", Toast.LENGTH_LONG).show();
            }
        } else if (view == change_profile_picture_btn) {
            Toast.makeText(SettingsActivity.this, "Unfortunately this has not been implemented yet.", Toast.LENGTH_LONG).show();
        } else if (view == copy_id_btn) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Your ID", userID);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(ctx, "ID: \"" + userID + " added to clipboard.", Toast.LENGTH_LONG).show();
        }
    }

    public void findViews() {
        // BUTTONS
        change_profile_picture_btn = findViewById(R.id.change_profile_picture);
        change_profile_picture_btn.setOnClickListener(this);
        save_password_btn = findViewById(R.id.save_password_btn);
        save_password_btn.setOnClickListener(this);
        save_changes_btn = findViewById(R.id.save_changes_btn);
        save_changes_btn.setOnClickListener(this);
        log_out_btn = findViewById(R.id.btn_log_out);
        log_out_btn.setOnClickListener(this);
        copy_id_btn = findViewById(R.id.copy_id_btn);
        // EDITTEXT
        profile_name_edit_text = findViewById(R.id.profile_name);
        profile_mail_edit_text = findViewById(R.id.profile_mail);
        profile_top_genre_edit_text = findViewById(R.id.profile_top_genre);
        profile_password_edit_text = findViewById(R.id.profile_password);
        profile_new_password_edit_text = findViewById(R.id.profile_new_password);
        profile_repeat_new_password_edit_text = findViewById(R.id.profile_repeat_new_password);
        // IMAGEVIEW
        profile_picture = findViewById(R.id.profile_picture);
        // TEXTVIEW
        profile_id = findViewById(R.id.profile_id);

    }
}
