package com.example.film_med_venner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IDatabase;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    private Controller_User auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_main);



        auth = Controller_User.getInstance();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Boolean facebookLoggedIn = accessToken != null && !accessToken.isExpired();


        //Comment out to not skip log in screen
       if (Controller_User.getInstance().isLoggedIn() || facebookLoggedIn) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }



        EditText username_input_editText = findViewById(R.id.input_username);
        EditText password_input_editText = findViewById(R.id.input_password);
        Button login_using_mail_btn = findViewById(R.id.btn_login_using_mail);
        TextView tv = findViewById(R.id.textview_forgot_password);

        login_using_mail_btn.setOnClickListener(view -> {
            try {
                auth.logIn(username_input_editText.getText().toString(), password_input_editText.getText().toString(), () -> {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                });
            } catch (IDatabase.DatabaseException e) {
                Toast.makeText(MainActivity.this, "Prøv igen!", Toast.LENGTH_LONG).show();
            }
        });

        login_using_mail_btn = findViewById(R.id.btn_signup_using_mail);
        login_using_mail_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivityWithMail.class);
            startActivity(intent);

        });

        tv.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });


        com.facebook.CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginButton continue_using_fb_btn = findViewById(R.id.btn_signup_using_facebook);
        continue_using_fb_btn.setReadPermissions(Arrays.asList("email"));
        continue_using_fb_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("MainActFjæsBog","Hey it succeed");
                try {
                    Database.getInstance().createFacebookUser(username_input_editText.getText().toString(),loginResult.getAccessToken(), () -> {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    });
                } catch (IDatabase.DatabaseException e) {
                    Toast.makeText(MainActivity.this,"Failed to log into Facebook",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancel() {
                Log.e("MainActFacebook","Facebook confirmed canceled");

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("MainActFacebook","wat",error);
            }
        });

    }



    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}