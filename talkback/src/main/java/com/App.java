package com;

import android.app.Application;

import com.google.android.libraries.accessibility.utils.log.FileHandler;
import com.google.android.libraries.accessibility.utils.log.LogUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the FileHandler with the application context and a file name
        FileHandler.initialize(this, "log.txt");
        LogUtils.init();
    }
}
