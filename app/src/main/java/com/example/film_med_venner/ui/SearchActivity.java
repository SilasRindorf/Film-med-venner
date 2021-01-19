package com.example.film_med_venner.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.R;
import com.example.film_med_venner.Utility;
import com.example.film_med_venner.controllers.Controller_Movie;
import com.example.film_med_venner.controllers.Controller_MovieDetails;
import com.example.film_med_venner.ui.adapters.SearchAdapter;
import com.example.film_med_venner.ui.fragments.Nav_bar_frag;
import com.example.film_med_venner.controllers.Controller_Search;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private GridView gridView;
    private SearchAdapter searchAdapter;
    private Context ctx;
    private EditText search;
    private ImageButton searchButton;
    private List<Movie> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        ctx = this;

        search = findViewById(R.id.searchField);
        search.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    setupGridView();
                    return true;
                }
            }
            return false;
        });

        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        Fragment frag = new Nav_bar_frag();
        addFrag(R.id.nav_bar_container,frag);

        gridView = findViewById(R.id.gridView);
    }

    @Override
    public void onClick(View v) {

        if (searchButton == v) {
            setupGridView();
        }
    }

    public void itemOnClick(View view) {
        int position = gridView.getPositionForView(view);
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("Id", items.get(position).getImdbID());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void addFrag(int id, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupGridView() {
        items = Controller_Search.getInstance().getSearchItems(search.getText().toString());
        searchAdapter = new SearchAdapter(ctx, items);
        gridView.setAdapter(searchAdapter);
        gridView.setVisibility(View.VISIBLE);
        Utility.hideKeyboard(SearchActivity.this);
    }
}
