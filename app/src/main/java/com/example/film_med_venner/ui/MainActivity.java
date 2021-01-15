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
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
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


        //Comment out to not skip log in screen
       /* if (Controller_User.getInstance().isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        */

        EditText ete = findViewById(R.id.input_username);
        EditText etp = findViewById(R.id.input_password);
        Button btn = findViewById(R.id.btn_login_using_mail);
        TextView tv = findViewById(R.id.textview_forgot_password);

        btn.setOnClickListener(view -> {
            try {
                auth.logIn(ete.getText().toString(), etp.getText().toString(), () -> {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                });
            } catch (IDatabase.DatabaseException e) {
                Toast.makeText(MainActivity.this, "Prøv igen!", Toast.LENGTH_LONG).show();
            }
        });

        btn = findViewById(R.id.btn_signup_using_mail);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivityWithMail.class);
            startActivity(intent);

        });

        tv.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });


        com.facebook.CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.btn_signup_using_facebook);
        loginButton.setReadPermissions(Arrays.asList("email"));
        Log.e("MainActF","I was here");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("MainActFjæsBog","Hey it succeed");
                try {
                    Database.getInstance().logInWithFaceBook(loginResult.getAccessToken(), () -> {
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