package com.example.film_med_venner.interfaces.IController;

import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IWatchItem;

import java.util.ArrayList;

public interface IProfileController extends IController {
    ArrayList<IProfile> getFriendItems();
    ArrayList<IWatchItem> getWatchedListItems();
    ArrayList<IWatchItem> getToWatchlistItems();
    // Review / Rating
    //ArrayList<IRating> getRatingItems();
    //IRating[] getReviewItems();
}
