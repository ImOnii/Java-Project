package com.shqip.tv.oni.activities;

import android.app.Application;

import com.onesignal.OneSignal;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        OneSignal.startInit(this)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

    }

}
