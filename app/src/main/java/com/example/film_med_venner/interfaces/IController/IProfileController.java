package com.example.film_med_venner.interfaces.IController;

import com.example.film_med_venner.interfaces.IProfile;
import com.example.film_med_venner.interfaces.IRating;
import com.example.film_med_venner.interfaces.IReview;
import com.example.film_med_venner.interfaces.IWatchedlistItem;
import com.example.film_med_venner.interfaces.IWatchlistItem;

import java.util.ArrayList;

public interface IProfileController extends IController {
    ArrayList<IProfile> getFriendItems();
    ArrayList<IWatchedlistItem> getWatchedListItems();
    ArrayList<IWatchlistItem> getToWatchlistItems();
    ArrayList<IRating> getRatingItems();
    ArrayList<IReview> getReviewItems();
}
