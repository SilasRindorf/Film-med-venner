package com.example.film_med_venner.API;

import com.example.film_med_venner.DAO.Movie;
import com.example.film_med_venner.DAO.MovieList;
import com.example.film_med_venner.interfaces.IMovie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import io.sentry.Sentry;

public class OmdbWebServiceClient {

    public static final String URL = "https://www.omdbapi.com/?apikey=a25b01e1";

    public OmdbWebServiceClient() {
    }

    public String sendGetRequest(String requestUrl) {
        StringBuffer response = new StringBuffer();

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) != null) {
                response.append(line);
            }
            buffer.close();
            connection.disconnect();

        } catch (IOException e) {
            Sentry.captureException(e);
        }

        return response.toString();
    }

    public Movie findMovieById(String id) {
        try {
            id = URLEncoder.encode(id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Sentry.captureException(e);

        }
        String requestUrl = URL + "&i=" + id;

        String response = sendGetRequest(requestUrl);
        return parseJSON(response);
    }

    public Movie parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Movie movie = gson.fromJson(response, Movie.class);
        return movie;
    }

    public List<Movie> searchMovieByTitle(String title) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Sentry.captureException(e);

        }
        String requestUrl = URL + "&s=" + title;
        String response = sendGetRequest(requestUrl);
        return arrayParseJSON(response);
    }

    public List<Movie> arrayParseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        MovieList movieList = gson.fromJson(response, MovieList.class);
        return movieList.getMovieList();
    }

}
