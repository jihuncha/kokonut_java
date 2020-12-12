package com.huni.engineer.kokonutjava.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.huni.engineer.kokonutjava.common.data.DailyFoodInfo;

import java.util.ArrayList;
import java.util.List;

public class TBL_DAILY_FOOD_DATA extends TABLE<DailyFoodInfo> {
    /*** Table 이름 정의 *******************/
    public static final String TABLE_NAME = "tbl_daily_food_data";

    /*** Column 이름 정의 **********************/
    public static final String ID                       = "_id";
    public static final String FOOD_NAME                = "food_name";                      //음식이름
    public static final String CALORIES                 = "calories";                       //총 칼로리
    public static final String CARBOHYDRATE             = "carbohydrate";                   //탄수화물
    public static final String PROTEIN                  = "protein";                        //단백질
    public static final String FAT                      = "fat";                            //지방
    public static final String IMAGE_KEY                = "image_key";                      //imageKey
    public static final String DATE                     = "date";                           //년월일 정보
    public static final String CONSUME_TIME             = "consume_time";                   //아침 점심 저녁


    /*** Table 생성 쿼리 **********************/
    public static final String CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
            "(" +
                ID						                            + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FOOD_NAME			                                + " TEXT NOT NULL," +
                CALORIES                                            + " INTEGER," +
                CARBOHYDRATE		                                + " INTEGER," +
                PROTEIN					                            + " INTEGER," +
                FAT                                                 + " INTEGER," +
                IMAGE_KEY                                           + " TEXT," +
                DATE                                                + " TEXT," +
                CONSUME_TIME			                            + " INTEGER DEFAULT 0" +
            ");";

    /*** Table 생성 쿼리 **********************/
    public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /*** INDEX 정의 **********************/
    protected static class INDEX {
        public int ID, FOOD_NAME, CALORIES, CARBOHYDRATE, PROTEIN, FAT, IMAGE_KEY, DATE, CONSUME_TIME;
    }

    protected static INDEX cursorToIndex(Cursor c) throws Exception {
        INDEX idx = new INDEX();
        idx.ID = c.getColumnIndex(ID);
        idx.FOOD_NAME = c.getColumnIndex(FOOD_NAME);
        idx.CALORIES = c.getColumnIndex(CALORIES);
        idx.CARBOHYDRATE = c.getColumnIndex(CARBOHYDRATE);
        idx.PROTEIN = c.getColumnIndex(PROTEIN);
        idx.FAT = c.getColumnIndex(FAT);
        idx.IMAGE_KEY = c.getColumnIndex(IMAGE_KEY);
        idx.DATE = c.getColumnIndex(DATE);
        idx.CONSUME_TIME = c.getColumnIndex(CONSUME_TIME);
        return idx;
    }

    /*** 생성자 *************************/
    public TBL_DAILY_FOOD_DATA(SQLiteDatabase db) {
        super(db, TABLE_NAME);
    }

    long parseLong(String s) {
        try {
            return Long.parseLong(s);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 오브젝트를 ContentValues로 변환한다.
     * @param o
     * @return
     */
    @Override
    public ContentValues fetchObject2Values(DailyFoodInfo o) {
        ContentValues values = new ContentValues();
        values.put(FOOD_NAME, o.getFoodName());
        values.put(CALORIES, o.getCalories());
        values.put(CARBOHYDRATE, o.getCarbohydrate());
        values.put(PROTEIN, o.getProtein());
        values.put(FAT, o.getFat());
        values.put(IMAGE_KEY, o.getImageKey());
        values.put(DATE, o.getDate());
        values.put(CONSUME_TIME, o.getConsumeTime());
        return values;
    }

    /**
     * 커서를 받아 리스트로 반환한다.
     * @param c
     * @return
     * @throws Exception
     */
    @Override
    public List<DailyFoodInfo> fetchCursor2List(Cursor c) throws Exception {
        List<DailyFoodInfo> list = new ArrayList<DailyFoodInfo>();
        int count = c.getCount();
        if (count == 0) {
            Log.d(TABLE_NAME, "fetchCursor2List() - count is zero.");
            return list;
        }

        if (c.moveToFirst()) {
            INDEX idx = cursorToIndex(c);
            do {
                DailyFoodInfo contact = new DailyFoodInfo();
                if (idx.FOOD_NAME != -1) contact.setFoodName(c.getString(idx.FOOD_NAME));
                if (idx.CALORIES != -1) contact.setCalories(c.getInt(idx.CALORIES));
                if (idx.CARBOHYDRATE != -1) contact.setCarbohydrate(c.getInt(idx.CARBOHYDRATE));
                if (idx.PROTEIN != -1) contact.setProtein(c.getInt(idx.PROTEIN));
                if (idx.FAT != -1) contact.setFat(c.getInt(idx.FAT));
                if (idx.IMAGE_KEY != -1) contact.setImageKey(c.getString(idx.IMAGE_KEY));
                if (idx.DATE != -1) contact.setDate(c.getString(idx.DATE));
                if (idx.CONSUME_TIME != -1) contact.setConsumeTime(c.getInt(idx.CONSUME_TIME));
                list.add(contact);
            }
            while (c.moveToNext());
        }

        return list;
    }

    public int insertForSync(List<DailyFoodInfo> addList) {
        try {
            int count = 0;
            db.beginTransaction();
            for (DailyFoodInfo contact : addList) {
                if (insert(contact) > 0) {
                    count++;
                }
            }
            db.setTransactionSuccessful();
            Log.d(TABLE_NAME, "insertForSync() - count: " + count);
            return count;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }

        Log.d(TABLE_NAME, "insertForSync() - count: " + -1);

        return -1;
    }

//    public int deleteForSync(List<DailyFoodInfo> delList) {
//        try {
//            int count = 0;
//            db.beginTransaction();
//            for (DailyFoodInfo contact : delList) {
//                int n = delete(E164_HASH + "=?", new String[] { contact.getE164Hash() });
//                if (n != -1) {
//                    count += n;
//                }
//            }
//            db.setTransactionSuccessful();
//
//            Log.d(TABLE_NAME, "deleteForSync() - count: " + count);
//            return count;
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            db.endTransaction();
//        }
//
//        Log.d(TABLE_NAME, "deleteForSync() - count: " + -1);
//
//        return -1;
//    }

//    public int updateForSync(List<DailyFoodInfo> modList) {
//        try {
//            int count = -1;
//            db.beginTransaction();
//            for (DailyFoodInfo contact : modList) {
//                if (update(contact, E164_HASH + "=?", new String[] { contact.getE164Hash() })) {
//                    count++;
//                }
//                else if (insert(contact) > 0) {
//                    count++;
//                }
//            }
//            db.setTransactionSuccessful();
//            Log.d(TABLE_NAME, "updateForSync() - count: " + count);
//            return count;
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            db.endTransaction();
//        }
//
//        Log.d(TABLE_NAME, "updateForSync() - count: " + -1);
//
//        return -1;
//    }

//    public int updateForSyncFromServer(List<JSScsContactValue> syncList) {
//        try {
//            int count = 0;
//            db.beginTransaction();
//            for (JSScsContactValue value : syncList) {
//                ContactObject contact = new ContactObject();
//                contact.setContactZzaltok(value);
//                if (update(contact, E164_HASH + "=?", new String[] { contact.getE164Hash() })) {
//                    count++;
//                }
//                else if (insert(contact) > 0) {
//                    count++;
//                }
//            }
//            db.setTransactionSuccessful();
//            Log.d(TABLE_NAME, "updateForSyncFromServer() - count: " + count);
//            return count;
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            db.endTransaction();
//        }
//
//        Log.d(TABLE_NAME, "updateForSyncFromServer() - count: " + -1);
//
//        return -1;
//    }
}
