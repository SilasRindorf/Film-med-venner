package com.example.film_med_venner.interfaces.runnable;

import com.example.film_med_venner.interfaces.IDatabase;

public interface RunnableErrorUI {
    void run();
    void handleError(IDatabase.DatabaseException e);
}
