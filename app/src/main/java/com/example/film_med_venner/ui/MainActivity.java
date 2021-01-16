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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private Controller_User auth;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_main);



        auth = Controller_User.getInstance();

        /*AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean facebookLoggedIn = accessToken != null && !accessToken.isExpired();


        //Comment out to not skip log in screen
       if (Controller_User.getInstance().isLoggedIn() || facebookLoggedIn) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }*/



        EditText username_input_editText = findViewById(R.id.input_username);
        EditText password_input_editText = findViewById(R.id.input_password);
        Button login_using_mail_btn = findViewById(R.id.btn_login_using_mail);
        TextView forget_password_textView = findViewById(R.id.textview_forgot_password);

        login_using_mail_btn.setOnClickListener(view -> {
            try {
                auth.logIn(username_input_editText.getText().toString(), password_input_editText.getText().toString(), () -> {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                });
            } catch (IDatabase.DatabaseException e) {
                Toast.makeText(MainActivity.this, "Prøv igen!", Toast.LENGTH_LONG).show();
            }
        });

        login_using_mail_btn = findViewById(R.id.btn_signup_using_mail);
        login_using_mail_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivityWithMail.class);
            startActivity(intent);

        });

        forget_password_textView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });


        callbackManager = CallbackManager.Factory.create();
        LoginButton continue_using_fb_btn = findViewById(R.id.btn_signup_using_facebook);
        continue_using_fb_btn.setPermissions("public_profile");
        continue_using_fb_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
            public void onSuccess(LoginResult loginResult) {
            //TODO Den når aldrig herind
            try {
                Database.getInstance().loginWithFacebookUser(loginResult.getAccessToken(), () -> {
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                String id = object.getString("id");
                                Log.e("TAG",object.getString("id"));
                                String name = (object.getString("first_name") + " " + object.getString("last_name"));
                                Log.e("TAG",object.getString("first_name") + " " + object.getString("last_name"));
                                String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";
                                Log.e("TAG","http://graph.facebook.com/" + id + "/picture?type=large");
                                String email = "N/A";
                                if (object.has("email")) {
                                    email = object.getString("email");
                                }
                                Log.e("TAG",object.getString("email"));
                            } catch (Exception e){
                                Log.e("TAG", String.valueOf(e));
                            }
                        }
                    });
                    Bundle fbUserData = new Bundle();
                    fbUserData.putString("fiels","id, name, image_url, email");
                    request.setParameters(fbUserData);
                    request.executeAsync();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                });
            } catch (IDatabase.DatabaseException e) {
                Toast.makeText(MainActivity.this,"Failed to log into Facebook",Toast.LENGTH_LONG).show();
            }
        }

            @Override
            public void onCancel() {
                Log.e("MainActFacebook","Facebook log in request canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("MainActFacebook","Error on Facebook login",error);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("A_Result: ", String.valueOf(callbackManager.onActivityResult(requestCode, resultCode, data)));
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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