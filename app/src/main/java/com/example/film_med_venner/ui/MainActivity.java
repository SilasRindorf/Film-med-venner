package com.example.film_med_venner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.DAO.Rating;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;


public class MainActivity extends AppCompatActivity {
    private Controller_User auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        auth = Controller_User.getInstance();


        //Comment out to not skip log in screen
/*        if (Controller_User.getInstance().isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }*/


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
            setContentView(R.layout.signup_using_mail);

            EditText username = findViewById(R.id.input_username);
            EditText firstName = findViewById(R.id.input_firstname);
            EditText surname = findViewById(R.id.input_surname);
            Button btnc = findViewById(R.id.btn_create_user);
            btnc.setOnClickListener(view -> {
                try {
                    Database.getInstance().createUser("email", "pass", username.getText().toString(), new RunnableErrorUI() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void handleError(IDatabase.DatabaseException e) {
                            switch (e.getErrorID()) {
                                case 101:
                                    Toast.makeText(MainActivity.this, "Password needs to be at least 6 characters", Toast.LENGTH_LONG).show();
                                    break;
                                case 102:
                                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                    break;
                                case 103:
                                    Toast.makeText(MainActivity.this, "There already exists an user with that email!", Toast.LENGTH_LONG).show();
                                    break;
                                case 104:
                                    Toast.makeText(MainActivity.this, "Invalid email!", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                } catch (IDatabase.DatabaseException e) {
                    Toast.makeText(MainActivity.this, "Try again!", Toast.LENGTH_LONG).show();
                }
            });
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