package com.example.film_med_venner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;

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
        if (view == log_out_btn) {
            try {
                Database.getInstance().logOut(() -> {
                    Intent intent = new Intent(/*org class*/this, /*Log In Screen*/MainActivity.class);
                    startActivity(intent);
                });
            } catch (IDatabase.DatabaseException e) {
                e.printStackTrace();
            }
        } else if (view == save_changes_btn) {

            try {
                Database.getInstance().updateUser(profile_name_edit_text.getText().toString(), profile_mail_edit_text.getText().toString(), profile_top_genre_edit_text.getText().toString(), new RunnableErrorUI() {
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
                Database.getInstance().updateUserPassword(profile_password_edit_text.getText().toString(), profile_new_password_edit_text.getText().toString(), new RunnableErrorUI() {
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
        profile_mail_edit_text = findViewById(R.id.profile_mail);
        profile_top_genre_edit_text = findViewById(R.id.profile_top_genre);
        profile_password_edit_text = findViewById(R.id.profile_password);
        profile_new_password_edit_text = findViewById(R.id.profile_new_password);
        profile_repeat_new_password_edit_text = findViewById(R.id.profile_repeat_new_password);
        // IMAGEVIEW
        profile_picture = findViewById(R.id.profile_picture);
    }
}
