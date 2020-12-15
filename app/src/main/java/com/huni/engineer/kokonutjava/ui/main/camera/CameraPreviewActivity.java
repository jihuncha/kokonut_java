package com.huni.engineer.kokonutjava.ui.main.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.utils.DisplayUtil;

public class CameraPreviewActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = CameraPreviewActivity.class.getSimpleName();

    private Context mContext;
    private Handler mHandler;

    private String  mContentPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        //DisplayUtil.setStatusBarWithColor(this, Color.TRANSPARENT);
        DisplayUtil.setStatusBarWithTransparent(this, Color.TRANSPARENT);

        setContentView(R.layout.activity_camera_preview);

        mContext = this;
        mHandler = new Handler();

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "onCreate() - intent NULL!!!");
            finish();
            return;
        }

        mContentPath = intent.getStringExtra(CameraCaptureActivity.EXTRA_PATH);
        if (TextUtils.isEmpty(mContentPath)) {
            Log.e(TAG, "onCreate(): intent is NULL!!!");
            finish();
            return;
        }

        initComponent();

//        GlideUtil.loadImage(mContext, iv_picture, mContentPath, true, "onCreate");
    }

    private void initComponent() {

    }

    @Override
    public void onClick(View view) {

    }
}
