package com.example.film_med_venner.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import com.example.film_med_venner.R;

public class LoadingScreen extends Activity {
    private boolean keep;

    @Override
    protected void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.loading_screen);
        Intent intent = this.getIntent();
        keep = intent.getExtras().getBoolean("finished");
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        keep = intent.getExtras().getBoolean("keep");
        if(!keep)
        {
            LoadingScreen.this.finish();
        }
    }


}
