package com.example.film_med_venner.interfaces;

import java.util.ArrayList;

public interface IProfile {
    //TODO Obs. på at det er ens egne reviews, ratings, to watch list, watched list, og friends man skal se når man klikker ind på en vilkårlig kategori.
    int getID();
    String getName();
    ArrayList<String> getMvgPrefs();
    ArrayList<IProfile> getFriends();
}
