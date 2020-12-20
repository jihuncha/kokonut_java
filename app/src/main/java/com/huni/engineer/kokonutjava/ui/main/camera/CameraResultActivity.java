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
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.proto.JSUtil;
import com.huni.engineer.kokonutjava.proto.response.JSresponseAnalyze;
import com.huni.engineer.kokonutjava.ui.popup.BottomDialog;
import com.huni.engineer.kokonutjava.utils.DisplayUtil;
import com.huni.engineer.kokonutjava.utils.GlideUtil;

public class CameraResultActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = CameraResultActivity.class.getSimpleName();

    private Context mContext;
    private Handler mHandler;

    private ImageView iv_food_info;

    private JSresponseAnalyze myData;

    //하단 dialog
    private BottomDialog mBottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);

        mContext = this;
        mHandler = new Handler();
        mBottomDialog = new BottomDialog();
        mBottomDialog.show(getSupportFragmentManager(), TAG);
        mBottomDialog.setListenerClose(new BottomDialog.OnCloseModal() {
            @Override
            public void onCloseModal() {

            }
        });

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "onCreate() - intent NULL!!!");
            finish();
            return;
        }

        myData = JSUtil.json2Object(intent.getStringExtra("info"), JSresponseAnalyze.class);

        Log.d(TAG, "myData: " + myData.toString());

//        mContentPath = intent.getStringExtra(CameraCaptureActivity.EXTRA_PATH);
//        if (TextUtils.isEmpty(mContentPath)) {
//            Log.e(TAG, "onCreate(): intent is NULL!!!");
//            finish();
//            return;
//        }
//
//        Log.d(TAG, "check - " + mContentPath);

        initComponent();
    }

    private void initComponent() {
        Log.d(TAG, "initComponent");

        iv_food_info = (ImageView) findViewById(R.id.iv_food_info);




        LoadImage();
    }

    private void LoadImage() {
        Log.d(TAG, "LoadImage");

        GlideUtil.loadImage(mContext, iv_food_info, myData.getImageKey(), false, "test");
    }


    @Override
    public void onClick(View view) {

    }
}
