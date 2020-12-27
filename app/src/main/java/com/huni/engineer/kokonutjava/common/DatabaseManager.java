package com.huni.engineer.kokonutjava.common;

import android.content.Context;
import android.util.Log;

import com.huni.engineer.kokonutjava.common.data.DailyFoodInfo;
import com.huni.engineer.kokonutjava.database.DatabaseHelper;
import com.huni.engineer.kokonutjava.database.TBL_DAILY_FOOD_DATA;
import com.huni.engineer.kokonutjava.proto.JSFoodInfo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static final String TAG = DatabaseManager.class.getSimpleName();

    private volatile static DatabaseManager sInstance;

    private static Context mContext = null;
    private static DatabaseHelper mHelper = null;

    private TBL_DAILY_FOOD_DATA mTblDailyFoodData;

    private DatabaseManager(Context context) {
        mHelper = new DatabaseHelper(context);
        mContext = context;

        mTblDailyFoodData = new TBL_DAILY_FOOD_DATA(mHelper.getWritableDatabase());
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

        mTblDailyFoodData.truncate();
    }

    public TBL_DAILY_FOOD_DATA getmTblDailyFoodData() {
        return mTblDailyFoodData;
    }


//    public JSFoodInfo getWeeklyLastData(String input) {
//        return mTblWeeklySales.getWeeklyLastData(input);
//    }

    //auth 이후에 프로필을 새로 setting
    public void insertFoodInfo(DailyFoodInfo insertObject) {
        Log.d(TAG, "insertFoodInfo - " + insertObject.toString());

        mTblDailyFoodData.updateForSync(insertObject);

        //UI에게 알린다.
//        PTTIntent.sendUITouchEvent(mContext, PTTDefine.TOUCH_REASON.I_UI_CONTACT_REFRESHED, "updateModifiedName");
    }

    public List<DailyFoodInfo> getMyFoodInfoAll() {
        Log.d(TAG, "getMyFoodInfoAll");

        List<DailyFoodInfo> myProfileList = new ArrayList<>();

        myProfileList = mTblDailyFoodData.getMyFoodInfoAll();

        Log.d(TAG,"getMyFoodInfoAll/result - " + myProfileList.toString());

        return myProfileList;
    }

    public List<DailyFoodInfo> getFoodDataForDate(String startDate, String endDate) {
        Log.d(TAG, "getFoodDataForDate - " + startDate + ", end - " + endDate);

        List<DailyFoodInfo> myFoodList = new ArrayList<>();

        myFoodList = mTblDailyFoodData.getDataBetweenDate(startDate, endDate);

        Log.d(TAG, "getFoodDataForDate - " + myFoodList);

        return myFoodList;

    }
}
