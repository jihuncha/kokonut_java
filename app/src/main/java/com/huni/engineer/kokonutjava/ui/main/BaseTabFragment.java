package com.huni.engineer.kokonutjava.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


public class BaseTabFragment {
    private String TAG() { return getClass().getSimpleName(); }

    protected Activity mActivity;
    protected Context  mContext;
    protected LayoutInflater mInflater;
    protected View mMyView;

    public BaseTabFragment(Activity activity, LayoutInflater inflater, int layoutId) {
        mActivity = activity;
        mContext  = activity;
        mInflater = inflater;

        mMyView = inflater.inflate(layoutId, null, false);
    }

    public View getView() {
        return mMyView;
    }

    public void processClickEvent(int viewId) {
        Log.d(TAG(), "processClickEvent()");
    }

    public void update(int reason) {
        Log.d(TAG(), "update()");
    }

    public void onStart() {
        Log.d(TAG(), "onStart()");
    }

    public void onResume() {
        Log.d(TAG(), "onResume()");
    }

    public void onPause() {
        Log.d(TAG(), "onPause()");
    }

    public void onStop() {
        Log.d(TAG(), "onPause()");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG(), "onActivityResult() - requestCode: " + requestCode
                + ", resultCode: " + resultCode);
    }
}
