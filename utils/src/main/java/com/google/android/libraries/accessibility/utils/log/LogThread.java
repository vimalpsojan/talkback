package com.google.android.libraries.accessibility.utils.log;

import android.os.HandlerThread;

public class LogThread extends HandlerThread {

    private static String TAG = "LogThread";

    public LogThread(){
        super(TAG, android.os.Process.THREAD_PRIORITY_DEFAULT);
    }

}
