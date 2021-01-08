package com.example.film_med_venner.DAO;
import com.example.film_med_venner.interfaces.IMovie;
import java.util.ArrayList;

public class Movie implements IMovie {
    private String title;
    private String info;
    private int ID;
    private String[] genres;
    private String posterPos;

    private ArrayList<String> actors;
    private ArrayList<Integer> reviewIDs;



    private int friendsRating;

    public Movie(String title, String info, ArrayList<String> actors, String[] genres, String posterPos) {
        this.title = title;
        this.info = info;
        this.actors = actors;
        this.genres = genres;
        this.posterPos = posterPos;
        reviewIDs = new ArrayList<>();
        actors = new ArrayList<>();
    }

    public Movie(String title, String info, ArrayList<String> actors, String[] genres, String posterPos, int friendsRating) {
        this(title,info,actors,genres, posterPos);
        this.friendsRating = friendsRating;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setPosterPos(String posterPos) {
        this.posterPos = posterPos;
    }

    public void setFriendsRating(int friendsRating) {
        this.friendsRating = friendsRating;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSummary() {
        return null;
    }

    @Override
    public ArrayList<String> getDirectors() {
        return null;
    }

    @Override
    public double getRuntime() {
        return 0;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public ArrayList<String> getActors() {
        return actors;
    }

    @Override
    public String[] getGenres() {
        return genres;
    }

    @Override
    public String getPosterPos() {
        return posterPos;
    }

    public int getFriendsRating() {
        return friendsRating;
    }

    @Override
    public int[] getReviews() {
        return reviewIDs.stream().mapToInt(i -> i).toArray();
    }
}
