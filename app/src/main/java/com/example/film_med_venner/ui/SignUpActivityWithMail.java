package com.example.film_med_venner.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.film_med_venner.R;

public class SignUpActivityWithMail extends Activity implements OnClickListener{
    Button sign_up_btn, go_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_using_mail);
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
