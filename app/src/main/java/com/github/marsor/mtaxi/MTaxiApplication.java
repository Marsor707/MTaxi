package com.github.marsor.mtaxi;

import android.app.Application;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class MTaxiApplication extends Application {

    private static MTaxiApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MTaxiApplication getInstance() {
        return instance;
    }
}
