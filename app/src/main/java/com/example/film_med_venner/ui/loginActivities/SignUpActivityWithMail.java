package com.example.film_med_venner.ui.loginActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.ui.HomeActivity;

public class SignUpActivityWithMail extends Activity implements OnClickListener{
    private Button sign_up_btn, go_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_using_mail);

        EditText username = findViewById(R.id.input_username);
        EditText firstName = findViewById(R.id.input_firstname);
        EditText surname = findViewById(R.id.input_surname);
        EditText email = findViewById(R.id.input_username);
        go_back_btn = findViewById(R.id.btn_go_back);
        go_back_btn.setOnClickListener(this);
        sign_up_btn = findViewById(R.id.btn_signup);
        sign_up_btn.setOnClickListener(view -> {
            try {
                if ( !(
                        ((EditText) findViewById(R.id.input_password)).getText().toString().equals(
                        ((EditText) findViewById(R.id.input_repeat_password)).getText().toString())
                )){
                    throw new IDatabase.DatabaseException("Not matching passwords");
                }
                String pass =((EditText) findViewById(R.id.input_password)).getText().toString();
                Controller_User.getInstance().createUser(email.getText().toString(), pass, new Profile(firstName.getText().toString() + " " + surname.getText().toString(), ""), new RunnableErrorUI() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SignUpActivityWithMail.this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
                    }

                    @Override
                    public void handleError(IDatabase.DatabaseException e) {
                        switch (e.getErrorID()) {
                            case 101:
                                Toast.makeText(SignUpActivityWithMail.this, "Password needs to be at least 6 characters", Toast.LENGTH_LONG).show();
                                break;
                            case 102:
                                Toast.makeText(SignUpActivityWithMail.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                break;
                            case 103:
                                Toast.makeText(SignUpActivityWithMail.this, "There already exists an user with that email!", Toast.LENGTH_LONG).show();
                                break;
                            case 104:
                                Toast.makeText(SignUpActivityWithMail.this, "Invalid email!", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Log.e("SignUp",e.toString());
                                break;
                        }
                    }
                });
            } catch (IDatabase.DatabaseException e) {
                Toast.makeText(SignUpActivityWithMail.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view){
        if (view == sign_up_btn){
            System.out.println("You succesfully created an account. Now go back and login or something.");
        }
        else if (view == go_back_btn){
            setContentView(R.layout.login_main);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
