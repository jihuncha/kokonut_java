package com.huni.engineer.kokonutjava.common;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class FileManager {
    public static final String TAG = FileManager.class.getSimpleName();

    public static final String DIR_NAME_DATA     = "data/";

    public static String getImageDir(Context context, boolean createIfNot) {
        String dir = context.getFilesDir().getAbsolutePath() + "/" + DIR_NAME_DATA;

        if (createIfNot && !isDirectory(dir)) {
            mkdirs(dir, "getDataDir");
        }

        return dir;
    }

    public static boolean isDirectory(String path) {
        if (path == null || path.isEmpty()) {
            return true;
        }

        File file = new File(path);
        return file.isDirectory();

    }

    public static boolean mkdirs(String path, String f) {
        Log.v(TAG, "mkdirs() - f: " + f + ", path: " + path);
        try {
            File file = new File(path);
            if (!file.exists()) {
                return file.mkdirs();
            }

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
