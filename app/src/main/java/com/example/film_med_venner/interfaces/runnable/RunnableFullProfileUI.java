package com.example.film_med_venner.interfaces.runnable;

import com.example.film_med_venner.DTO.FullProfileDTO;
import com.example.film_med_venner.interfaces.IDatabase;

public interface RunnableFullProfileUI {
    void run(FullProfileDTO fullProfileDTO) throws IDatabase.DatabaseException;
}
