package com.huni.engineer.kokonutjava.common;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;

import com.huni.engineer.kokonutjava.ui.popup.CommonPopup;

public class PopupManager {
    public static final String TAG = PopupManager.class.getSimpleName();

    private volatile static PopupManager sInstance;

    private Context mContext = null;
    private Handler mHandler = null;

    private PopupManager(Context context) {
        mContext = context;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static PopupManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PopupManager.class) {
                if (sInstance == null) {
                    sInstance = new PopupManager(context);
                }
            }
        }
        return sInstance;
    }

    public CommonPopup showDialog(final Activity a, final String title,
                                  final String text, final int type, final boolean cancelable,
                                  final CommonPopup.IPopupMsgListener listener) {
        final CommonPopup popup = new CommonPopup(a, title, text, type, false);
        popup.setListener(listener);
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                popup.show();
            }
        });

        return popup;
    }
}
