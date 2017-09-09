package com.didi.dts.utils;

import android.util.Log;

import com.didi.dts.BuildConfig;

public class Logger {
    // ===========================================================
    // Constants
    // ===========================================================

    public static final String TAG = "com.didi.moli-log";
    public static final boolean DEBUG = BuildConfig.DEBUG;

    // ===========================================================
    // Public methods
    // ===========================================================	

    public static void l(String str) {
        if (DEBUG) {
            Log.i(TAG, str);
        }
    }

    public static void l(Exception e) {
        if (!DEBUG) {
            return;
        }

        Logger.l(e.toString());
        StackTraceElement[] elements = e.getStackTrace();
        if (null == elements) {
            return;
        }

        for (StackTraceElement element : elements) {
            if (null != element) {
                l(element.toString());
            }
        }
    }
}
