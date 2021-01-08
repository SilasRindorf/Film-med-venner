package com.example.film_med_venner.ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_Auth;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {
    private Controller_Auth auth = Controller_Auth.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);
        mAuth = FirebaseAuth.getInstance();
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


    public boolean isLoggedIn(){
        return mAuth.getCurrentUser() != null;
    }
    public void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task  -> {
            if(task.isSuccessful()){
                Log.d(TAG,"Create user with email: Success ");
            }
            else {
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
                    Log.e(TAG, e.getMessage());
                }

            }
        });

    }

}