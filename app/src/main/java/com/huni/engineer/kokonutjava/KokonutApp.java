package com.huni.engineer.kokonutjava;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.huni.engineer.kokonutjava.common.DatabaseManager;

public class KokonutApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //디버깅 툴 - 너는 모하는 아이니?
        Stetho.initializeWithDefaults(this);
        //DB 생성
        DatabaseManager.getInstance(KokonutApp.this);
    }
}
