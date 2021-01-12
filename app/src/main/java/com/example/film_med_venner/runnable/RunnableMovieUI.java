package com.example.film_med_venner.runnable;

import com.example.film_med_venner.interfaces.IMovie;

public interface RunnableMovieUI extends RunnableUI{
    void run(IMovie[] movies);
}
