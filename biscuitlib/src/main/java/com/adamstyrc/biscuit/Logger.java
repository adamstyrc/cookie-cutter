package com.adamstyrc.biscuit;

import android.util.Log;


/**
 * Created by adamstyrc on 31/03/16.
 */
public class Logger {

    public static void log(String message) {
        if (BuildConfig.DEBUG) {
            Log.d("Biscuit", message);
        }
    }
}
