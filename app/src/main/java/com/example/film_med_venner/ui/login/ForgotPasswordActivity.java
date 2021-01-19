package com.example.film_med_venner.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnreset;
    private Button btnback;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        editText = findViewById(R.id.edit_text_input_password_forgot_password);
        btnreset = findViewById(R.id.btn_resetPassword);
        btnback = findViewById(R.id.btn_PasswordBack);
// Hej Bubbi
        btnreset.setOnClickListener(this);
        btnback.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view == btnreset) {
            try {
                if (editText.getText().toString() != null) {
                    Controller_User.getInstance().sendPasswordEmail(editText.getText().toString());
                    Toast.makeText(ForgotPasswordActivity.this, "Email is sent", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                System.out.println("Something went wrong");
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong try again", Toast.LENGTH_LONG).show();
            }
        } else {
            Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
        }


    }
}