package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IProfile {
    int getID();
    String getName();
    ArrayList<String> getMvgPrefs();
    ArrayList<IProfile> getFriends();
}
