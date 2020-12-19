package com.huni.engineer.kokonutjava.ui.main.camera;

import android.app.Activity;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.utils.DisplayUtil;
import com.huni.engineer.kokonutjava.utils.GlideUtil;

public class CameraPreviewActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = CameraPreviewActivity.class.getSimpleName();

    private Context mContext;
    private Handler mHandler;

    private String  mContentPath = null;

    private PhotoView iv_picture;
    private TextView tv_bottom_cancel;
    private TextView tv_bottom_confirm;
    private ImageView iv_back;

    public static void startCameraPreview(Activity a, String path, int requestCode) {
        Intent intent = new Intent(a, CameraPreviewActivity.class);
//        intent.putExtra(CameraCaptureActivity.EXTRA_MODE, mode);
        intent.putExtra(CameraCaptureActivity.EXTRA_PATH, path);
        a.startActivityForResult(intent, requestCode);
    }

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
        iv_picture = (PhotoView) findViewById(R.id.iv_picture);
        tv_bottom_cancel = (TextView) findViewById(R.id.tv_bottom_cancel);
        tv_bottom_confirm = (TextView) findViewById(R.id.tv_bottom_confirm);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        GlideUtil.loadImage(mContext, iv_picture, mContentPath, true, "onCreate");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bottom_cancel:
                finish();
                break;
            case R.id.tv_bottom_confirm:
                //TODO 서버연동이후 다음 activity 로 이동


                break;


        }
    }
}
