package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.ISearch;

public class Search implements ISearch {
    private String category;
    private String movie;

    public Search(String category, String movie){
        this.category = category;
        this.movie = movie;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getMovie() {
        return movie;
    }

}
