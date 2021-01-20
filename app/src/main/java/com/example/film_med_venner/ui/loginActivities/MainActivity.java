package com.example.film_med_venner.ui.loginActivities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.film_med_venner.DAO.Profile;
import com.example.film_med_venner.R;
import com.example.film_med_venner.controllers.Controller_User;
import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.ui.HomeActivity;
import com.example.film_med_venner.ui.LoadingScreen;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import io.sentry.Sentry;


public class MainActivity extends AppCompatActivity {
    private Controller_User auth;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sentry.captureMessage("testing SDK setup");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_main);
        /**
         * https://developers.facebook.com/docs/android/getting-started#sig
         * Søg på "KeyHash" i Run. Tag det key der er blevet lavet, og smæk det ind her: https://developers.facebook.com/settings/developer/sample-app/
         * og derudover under "Key Hashes" her: https://developers.facebook.com/apps/412664376620411/settings/basic/
         */
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Sentry.captureException(e);

        }
        auth = Controller_User.getInstance();
        // Force logout in case of error
        /*try {
            auth.logOut(() -> {
            });
        } catch (IDatabase.DatabaseException e) {
            e.printStackTrace();
        }*/

        //Comment out to not skip log in screen
        if (Controller_User.getInstance().getCurrentUser() != null || Controller_User.getInstance().isFacebookUserLoginValid()) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
        }


        EditText username_input_editText = findViewById(R.id.input_username);
        EditText password_input_editText = findViewById(R.id.input_password);
        Button login_using_mail_btn = findViewById(R.id.btn_login_using_mail);
        TextView forget_password_textView = findViewById(R.id.textview_forgot_password);

        // LOGIN
        login_using_mail_btn.setOnClickListener(view -> {
            try {
                auth.logIn(username_input_editText.getText().toString(), password_input_editText.getText().toString(), () -> {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityIfNeeded(intent, 0);
                });
            } catch (IDatabase.DatabaseException e) {
                Sentry.captureException(e);
                Toast.makeText(MainActivity.this, "Prøv igen!", Toast.LENGTH_LONG).show();
            }
        });

        // SIGNUP
        Button sign_up_using_mail_btn = findViewById(R.id.btn_signup_using_mail);
        sign_up_using_mail_btn.setOnClickListener(v -> {
            setContentView(R.layout.signup_using_mail);
            Intent intent = new Intent(MainActivity.this, SignUpActivityWithMail.class);
            startActivity(intent);

        });

        // FORGOT PASSWORD
        forget_password_textView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            setContentView(R.layout.activity_forgotpassword);
            startActivity(intent);
        });


        // FACEBOOK
        callbackManager = CallbackManager.Factory.create();
        LoginButton continue_using_fb_btn = findViewById(R.id.btn_signup_using_facebook);
        continue_using_fb_btn.setPermissions(Arrays.asList("public_profile", "email"));
        continue_using_fb_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent ld = new Intent(MainActivity.this, LoadingScreen.class);
                ld.putExtra("finished", true);
                ld.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ld.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(ld);
                try {
                    Controller_User.getInstance().loginWithFacebookUser(loginResult.getAccessToken(), () -> {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                            try {
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String email = "N/A";
                                if (object.has("email")) {
                                    email = object.getString("email");
                                }
                                String image_url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                //TODO Tilføj fb bruger i db måske vha. param bundle?
                                IProfile profile = new Profile(name, id);
                                Controller_User.getInstance().addFacebookUser(email, image_url, profile, new RunnableErrorUI() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        startActivityIfNeeded(intent, 0);
                                    }

                                    @Override
                                    public void handleError(IDatabase.DatabaseException e) {
                                        switch (e.getErrorID()) {
                                            case 101:
                                                Toast.makeText(MainActivity.this, "Password needs to be at least 6 characters", Toast.LENGTH_LONG).show();
                                                break;
                                            case 102:
                                                Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                                break;
                                            case 103:
                                                Toast.makeText(MainActivity.this, "There already exists an user with that email!", Toast.LENGTH_LONG).show();
                                                break;
                                            case 104:
                                                Toast.makeText(MainActivity.this, "Invalid email!", Toast.LENGTH_LONG).show();
                                                break;
                                            default:
                                                Log.e("SignUp", e.toString());
                                                e.printStackTrace();
                                                break;
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                Sentry.captureException(e);
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();
                        Toast.makeText(MainActivity.this, "Successfully logged in with your Facebook account", Toast.LENGTH_LONG).show();
                    });
                } catch (IDatabase.DatabaseException e) {
                    Sentry.captureException(e);
                    Toast.makeText(MainActivity.this, "Failed to log into Facebook", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancel() {
                Log.e("MainActFacebook", "Facebook log in request canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Sentry.captureException(error);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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