package com.example.film_med_venner.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.film_med_venner.R;

public class LoadingScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.loading_screen);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        boolean keep = intent.getExtras().getBoolean("keep");
        if(!keep)
        {
            LoadingScreen.this.finish();
        }
    }


}
