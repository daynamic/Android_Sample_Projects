package com.test.asharm93.messagingapp.protocol;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * Created by asharm93 on 7/8/2016.
 */
public class GlobalDataStore extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }
}
