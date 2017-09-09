package com.didi.dts.utils;

import android.app.Application;

public class App extends Application {

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Application methods
    // ===========================================================

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPref.init(this);
/*        if (!SharedPref.getBoolean(SharedPref.HANDLED_FIRST_TIME, false)) {
            SharedPref.putBoolean(SharedPref.HANDLED_FIRST_TIME, true);
            onFirstTime();
        }*/
    }
}
