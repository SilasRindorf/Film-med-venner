package com.example.film_med_venner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;



public class MainActivity extends AppCompatActivity {
    private Controller_User auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        auth = Controller_User.getInstance();


        //Comment out to not skip log in screen
        if (Controller_User.getInstance().isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        EditText ete = findViewById(R.id.input_username);
        EditText etp = findViewById(R.id.input_password);
        Button btn = findViewById(R.id.btn_login_using_mail);
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

        btn = findViewById(R.id.btn_login_using_facebook);
        btn.setOnClickListener(view -> {
            try {
                auth.logInWithFaceBook(ete.getText().toString(), etp.getText().toString(), () -> {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                });
            } catch (IDatabase.DatabaseException e) {
                Toast.makeText(MainActivity.this, "Prøv igen!", Toast.LENGTH_LONG).show();
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