package com.huni.engineer.kokonutjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
* SharedPreferences
*
* */
public class KokonutSettings {
    private volatile static KokonutSettings sInstance;

    private Context mContext;
    private SharedPreferences mSharedPrefs;

    private KokonutSettings(Context context) {
        this.mContext = context;
        this.mSharedPrefs = context.getSharedPreferences("kokonut_settings", Context.MODE_PRIVATE);
    }

    public static KokonutSettings getInstance(Context context) {
        if (sInstance == null) {
            synchronized (KokonutSettings.class) {
                if (sInstance == null) {
                    sInstance = new KokonutSettings(context);
                }
            }
        }

        return sInstance;
    }

    /* save 값들 */
    protected long save(String key, long value) {
        Log.d("kokonut_settings", "save - " + key + "=" + value);
        SharedPreferences.Editor edit = this.mSharedPrefs.edit();
        edit.putLong(key, value);
        edit.commit();
        return value;
    }

    protected int save(String key, int value) {
        Log.d("kokonut_settings", "save - " + key + "=" + value);
        SharedPreferences.Editor edit = this.mSharedPrefs.edit();
        edit.putInt(key, value);
        edit.commit();
        return value;
    }

    protected boolean save(String key, boolean value) {
        Log.d("kokonut_settings", "save - " + key + "=" + value);
        SharedPreferences.Editor edit = this.mSharedPrefs.edit();
        edit.putBoolean(key, value);
        edit.commit();
        return value;
    }

    protected String save(String key, String value) {
        Log.d("kokonut_settings", "save - " + key + "=" + value);
        SharedPreferences.Editor edit = this.mSharedPrefs.edit();
        edit.putString(key, value);
        edit.commit();
        return value;
    }

    //최초 실행 여부 관리
    public boolean getAlreadyStarted() 						 { return mSharedPrefs.getBoolean("alreadyStarted", false); }
    public boolean setAlreadyStarted(boolean alreadyStarted) { return                    save("alreadyStarted", alreadyStarted); }

    public String setSessionKey(String sessionKey) {
        return save("sessionKey", sessionKey);
    }

    public String getSessionKey()                       {return  mSharedPrefs.getString("sessionKey", "");}

//    public int getMondaySellAvg() {
//        return mSharedPrefs.getInt("mondaySellAvg", 0);
//    }
//    public int setMondaySellAvg(String input) {
//        return save("mondaySellAvg", Integer.parseInt(Tools.deleteComma(input)));
//    }
//
//    public int getTuesdaySellAvg() {
//        return mSharedPrefs.getInt("tuesdaySellAvg", 0);
//    }
//    public int setTuesdaySellAvg(String input) {
//        return save("tuesdaySellAvg", Integer.parseInt(Tools.deleteComma(input)));
//    }
//
//    public int getWednesdayySellAvg() {
//        return mSharedPrefs.getInt("wednesdaySellAvg", 0);
//    }
//    public int setWednesdaySellAvg(String input) {
//        return save("wednesdaySellAvg", Integer.parseInt(Tools.deleteComma(input)));
//    }
//
//    public int getThursdaySellAvg() {
//        return mSharedPrefs.getInt("thursdaySellAvg", 0);
//    }
//    public int setThursdaySellAvg(String input) {
//        return save("thursdaySellAvg", Integer.parseInt(Tools.deleteComma(input)));
//    }
//
//    public int getFridaySellAvg() {
//        return mSharedPrefs.getInt("fridaySellAvg", 0);
//    }
//    public int setFridaySellAvg(String input) {
//        return save("fridaySellAvg", Integer.parseInt(Tools.deleteComma(input)));
//    }
//
//    public int getSaturdaySellAvg() {
//        return mSharedPrefs.getInt("saturdaySellAvg", 0);
//    }
//    public int setSaturdaySellAvg(String input) {
//        return save("saturdaySellAvg", Integer.parseInt(Tools.deleteComma(input)));
//    }
//
//    public int getSundaySellAvg() {
//        return mSharedPrefs.getInt("sundaySellAvg", 0);
//    }
//    public int setSundaySellAvg(String input) {
//        return save("sundaySellAvg", Integer.parseInt(Tools.deleteComma(input)));
//    }

}
