package com.example.film_med_venner.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.R;
import com.example.film_med_venner.ui.adapters.SearchAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.controllers.SearchController;
import com.example.film_med_venner.interfaces.ISearch;
import com.example.film_med_venner.ui.profileActivities.ReviewActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    GridView gridView;
    private SearchAdapter searchAdapter;
    private Context ctx;
    ImageButton imageButton;
    SearchController controller = SearchController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        ctx = this;

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        gridView = findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);

    }
    public void setAllButtonListener(ViewGroup viewGroup) {
        View v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                setAllButtonListener((ViewGroup) v);
            } else if (v instanceof ImageButton) {
                //((ImageButton) v).setOnClickListener(onItemClick(?,?,?,?));
            }
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position) {
        setContentView(R.layout.activity_movie_details);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        startActivity(intent);
    }
*/

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupHomeFeed(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupHomeFeed(false);
    }

    void setupHomeFeed(boolean run) {
        AsyncTask asyncTask = new AsyncTask() {
            List<ISearch> items = new ArrayList<ISearch>();
            String errorMsg = null;

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    items = controller.getSearchItems();
                    return null;
                } catch (Exception e) {
                    //    errorMsg = e.getMessage();
                    e.printStackTrace();
                    return e;
                }
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected void onPostExecute(Object titler) {
                searchAdapter = new SearchAdapter(ctx, items);
                gridView.setAdapter(searchAdapter);
                gridView.setVisibility(View.VISIBLE);
            }

        };

        if (run) {
            asyncTask.execute();
        } else {
            asyncTask.cancel(true);
        }
    }



}
