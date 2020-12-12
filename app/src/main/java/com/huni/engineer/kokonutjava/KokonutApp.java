package com.huni.engineer.kokonutjava;

import android.app.Application;

import com.huni.engineer.kokonutjava.common.DatabaseManager;

public class KokonutApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //DB 생성
        DatabaseManager.getInstance(KokonutApp.this);
    }
}
