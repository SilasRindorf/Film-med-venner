package com.example.film_med_venner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {
    private Controller_User auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        auth = Controller_User.getInstance();

        //Comment out to not skip log in screen
        /*if (isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }*/


        EditText ete = findViewById(R.id.input_username);
        EditText etp = findViewById(R.id.input_password);
        Button btn = findViewById(R.id.btn_login_using_mail);
        btn.setOnClickListener(view -> {
            //@MortenCKruuse vi kan flytte Auth ud af UI nu
            try {
                auth.logIn(ete.getText().toString(), etp.getText().toString(), new RunnableUI() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
            } catch (IDatabase.DatabaseException e) {
                Toast.makeText(MainActivity.this, "Prøv igen!", Toast.LENGTH_LONG).show();
            }
        });


        /*EditText etce = findViewById(R.id.createUserEmailAddress);
        EditText etcp = findViewById(R.id.createUserPassword);
        EditText etcu = findViewById(R.id.createUserName);
        Button btnc = findViewById(R.id.btn_create_user);
        btnc.setOnClickListener(view -> {
            createUser(etce.getText().toString(), etcp.getText().toString());
        });*/
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
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



    public void createUser(String email, String password, String name) {/*
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Create user with email: Success ");
                    Controller_User.getInstance().addUser("name", auth.getCurrentUser().getUid());
                } else {
                    Log.d(TAG, "Create user with email: Failed ");
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        Toast.makeText(MainActivity.this, "Password skal være mindst 6 karaktere", Toast.LENGTH_LONG).show();
                        Log.e(TAG, e.getMessage());
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(MainActivity.this, "I", Toast.LENGTH_LONG).show();
                        Log.e(TAG, e.getMessage());

                    } catch (FirebaseAuthUserCollisionException e) {
                        Toast.makeText(MainActivity.this, "Der eksistere allerede en bruger", Toast.LENGTH_LONG).show();
                        Log.e(TAG, e.getMessage());
                    } catch (FirebaseAuthEmailException e) {
                        Toast.makeText(MainActivity.this, "Ikke valid email", Toast.LENGTH_LONG).show();
                        Log.e(TAG, e.getMessage());

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Der skete en fejl prøv igen", Toast.LENGTH_LONG).show();
                        Log.e(TAG, e.getMessage());
                    }

                }
            });
        } catch (IllegalArgumentException e) {
            Toast.makeText(MainActivity.this, "Email eller password må ikke være tomme!", Toast.LENGTH_LONG).show();
        }
        */
    }

}