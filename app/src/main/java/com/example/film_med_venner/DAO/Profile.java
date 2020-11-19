package com.example.film_med_venner.DAO;

import com.example.film_med_venner.interfaces.IProfile;

import java.util.ArrayList;

public class Profile implements IProfile {

    private int ID;
    private String name;
    private ArrayList<String> mvGPrefs;

    public Profile(String name, int ID){
        this.ID = ID;
        this.name = name;
        mvGPrefs = new ArrayList<>();
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    @Override
    public ArrayList<String> getMvgPrefs() {
        return mvGPrefs;
    }

    @Override
    public ArrayList<IProfile> getFriends() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMvGPrefs(ArrayList<String> mvGPrefs) {
        this.mvGPrefs = mvGPrefs;
    }


}
