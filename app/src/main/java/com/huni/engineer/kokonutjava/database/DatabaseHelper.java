package com.huni.engineer.kokonutjava.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = DatabaseHelper.class.getSimpleName();

    /*** DB 상수 정의 *******************************/
    public static final int DB_VERSION = 1;
    //
    public static final String DB_NAME = "zzaltok.db";

    public Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate()");
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade() - oldVersion: " + oldVersion + ", newVersion: " + newVersion);
        //oldVersion
        switch (oldVersion) {
            case 1:
            case 2:
                //upgradeXTo2(db);
                break;
        }
    }

    public void upgradeXTo2(SQLiteDatabase db) {
        Log.d(TAG, "upgradeXTo2()");
    }

    /**
     * 전체 TABLE을 생성한다.
     * @param db
     */
    private void createTables(SQLiteDatabase db) {
        //연락처
//        execSQL(db, TBL_CONTACT.CREATE, "createTables");

    }

    /**
     * 전체 TABLE을 삭제한다.
     * @param db
     */
    private void dropTables(SQLiteDatabase db) {
        //execSQL(db, TBL_CHANNEL.DROP, "dropTables");


    }

    private void execSQL(SQLiteDatabase db, String query, String f) {
        Log.d(TAG, "execSQL() - f: " + f + ", query: " + query);
        db.execSQL(query);
    }

}
