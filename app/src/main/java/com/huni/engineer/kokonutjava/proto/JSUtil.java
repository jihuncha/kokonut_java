package com.huni.engineer.kokonutjava.proto;

import com.google.gson.Gson;

public class JSUtil {
    private static Gson[] sGson = {
            new Gson(), new Gson(), new Gson(), new Gson(), new Gson(),
            new Gson(), new Gson(), new Gson(), new Gson(), new Gson(),
    };

    private static int sIndex = 0;

    private static synchronized int INDEX() {
        if ((sIndex += 1) >= 10000) {
            sIndex = 0;
        }
        return sIndex % sGson.length;
    }

    /**
     * JSON 문자열을 Object로 변환한다.
     * @param str
     * @param classOfT
     * @return
     */
    public static <T>T json2Object(String str, Class<T> classOfT) {
        int index = INDEX();
        synchronized (sGson[index]) {
            return sGson[index].fromJson(str, classOfT);
        }
    }

    /**
     * JSON Object를 문자열로 변환한다.
     * @param obj
     * @return
     */
    public static String json2String(Object obj) {
        int index = INDEX();
        synchronized (sGson[index]) {
            return sGson[index].toJson(obj);
        }
        //return new Gson().toJson(obj);
    }

}
