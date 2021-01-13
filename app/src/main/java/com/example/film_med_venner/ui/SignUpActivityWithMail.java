package com.example.film_med_venner.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.film_med_venner.R;
import com.example.film_med_venner.databases.Database;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;

public class SignUpActivityWithMail extends Activity implements OnClickListener{
    Button sign_up_btn, go_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_using_mail);

        EditText username = findViewById(R.id.input_username);
        EditText firstName = findViewById(R.id.input_firstname);
        EditText surname = findViewById(R.id.input_surname);
        Button btnc = findViewById(R.id.btn_signup);
        btnc.setOnClickListener(view -> {
            try {
                Database.getInstance().createUser("email", "pass", username.getText().toString(), new RunnableErrorUI() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SignUpActivityWithMail.this, HomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void handleError(IDatabase.DatabaseException e) {
                        switch (e.getErrorID()) {
                            case 101:
                                Toast.makeText(SignUpActivityWithMail.this, "Password needs to be at least 6 characters", Toast.LENGTH_LONG).show();
                                break;
                            case 102:
                                Toast.makeText(SignUpActivityWithMail.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                Log.e("SignUp",e.toString());
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
                Toast.makeText(SignUpActivityWithMail.this, "Try again!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view){
        if (view == sign_up_btn){
            System.out.println("You succesfully created an account. Now go back and login or something.");
        }
        else if (view == go_back_btn){
            switchActivity(LoginActivity.class);
        }
    }

    public void switchActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
