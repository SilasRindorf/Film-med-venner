package com.example.film_med_venner.interfaces.runnable;


import com.example.film_med_venner.interfaces.IRating;

public interface RunnableRatingUI extends RunnableUI {
    void run(IRating[] ratings);
    void run(IRating rating);
}
