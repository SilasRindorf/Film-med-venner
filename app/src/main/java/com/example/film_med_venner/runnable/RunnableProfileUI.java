package com.example.film_med_venner.runnable;

import com.example.film_med_venner.interfaces.IProfile;

public interface RunnableProfileUI {
    //Method to be overwritten when called as lambda ex: void function ( profile -> {} );
    void run(IProfile profile);

}
