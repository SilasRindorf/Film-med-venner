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
import com.example.film_med_venner.controllers.Controller_Auth;
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
        if (isLoggedIn()){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }


        EditText ete = findViewById(R.id.editTextTextEmailAddress);
        EditText etp = findViewById(R.id.editTextTextPassword);
        Button btn = findViewById(R.id.btn_login);
        btn.setOnClickListener(view ->{
            logIn(ete.getText().toString(),etp.getText().toString());
        });

        EditText etce = findViewById(R.id.createUserEmailAddress);
        EditText etcp = findViewById(R.id.createUserPassword);
        Button btnc = findViewById(R.id.btn_create_user);
        btnc.setOnClickListener(view ->{
            createUser(etce.getText().toString(),etcp.getText().toString());
        });
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

    public boolean isEmptyStringArray(String [] array){
        for(int i=0; i<array.length; i++){
            if(array[i]!=null){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void logIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task ->{
            if (task.isSuccessful()){
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
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