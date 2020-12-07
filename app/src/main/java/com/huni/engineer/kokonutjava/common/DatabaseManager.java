package com.huni.engineer.kokonutjava.common;

import android.content.Context;
import android.util.Log;

import com.huni.engineer.kokonutjava.database.DatabaseHelper;

public class DatabaseManager {
    public static final String TAG = DatabaseManager.class.getSimpleName();

    private volatile static DatabaseManager sInstance;

    private static Context mContext = null;
    private static DatabaseHelper mHelper = null;

    private DatabaseManager(Context context) {
        mHelper = new DatabaseHelper(context);
        mContext = context;
    }

    public static DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DatabaseManager.class) {
                if (sInstance == null) {
                    sInstance = new DatabaseManager(context);
                }
            }
        }
        return sInstance;
    }

    public static DatabaseHelper getDatabaseHelper(){
        if(mHelper == null){
            mHelper = new DatabaseHelper(mContext);
        }
        return mHelper;
    }


    /**
     * 테이블을 몽땅 truncate 시킨다.
     */
    public void truncate() {
        Log.d(TAG, "truncate()");

    }
}
