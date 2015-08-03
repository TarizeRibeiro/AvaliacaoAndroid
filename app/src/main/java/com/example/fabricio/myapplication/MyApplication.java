package com.example.fabricio.myapplication;

import android.app.Application;

import com.example.fabricio.myapplication.Util.AppUtil;

/**
 * Created by Fabricio on 23/07/2015.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        AppUtil.CONTEXT = getApplicationContext();
        super.onCreate();
    }
}
