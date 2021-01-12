package com.example.film_med_venner.runnable;


import com.example.film_med_venner.interfaces.IRating;

public interface RunnableRatingsUI extends RunnableUI {
    void run(IRating[] ratings);
}
