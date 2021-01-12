package com.example.film_med_venner.runnable;

import com.example.film_med_venner.interfaces.IReview;

public interface RunnableReviewUI extends RunnableUI {
    void run(IReview[] review);
}
