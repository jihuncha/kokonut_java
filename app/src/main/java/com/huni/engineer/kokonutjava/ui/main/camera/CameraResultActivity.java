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
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
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

    private ImageView iv_back_press;
    private PhotoView iv_food_info;
    private Button  bt_open_dialog;

    private String mPath = null;
    private JSresponseAnalyze myData;

    //하단 dialog
    private BottomDialog mBottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);

        mContext = this;
        mHandler = new Handler();

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "onCreate() - intent NULL!!!");
            finish();
            return;
        }

        myData = JSUtil.json2Object(intent.getStringExtra("info"), JSresponseAnalyze.class);
        mPath = intent.getStringExtra(CameraPreviewActivity.EXTRA_PATH);

        if (TextUtils.isEmpty(mPath)) {
            Log.e(TAG, "onCreate(): mPath is NULL!!!");
        }

        Log.d(TAG, "myData: " + myData.toString());

        mBottomDialog = new BottomDialog(mContext, myData);
        mBottomDialog.show(getSupportFragmentManager(), TAG);
        mBottomDialog.setListenerClose(new BottomDialog.OnCloseModal() {
            @Override
            public void onCloseModal() {

            }
        });


        initComponent();
    }

    private void initComponent() {
        Log.d(TAG, "initComponent");

        iv_back_press = (ImageView) findViewById(R.id.iv_back_press);
        iv_back_press.setOnClickListener(this);
        iv_food_info = (PhotoView) findViewById(R.id.iv_food_info);
        bt_open_dialog = (Button) findViewById(R.id.bt_open_dialog);
        bt_open_dialog.setOnClickListener(this);

        if (!TextUtils.isEmpty(mPath)){
            GlideUtil.loadImage(mContext, iv_food_info, mPath, false, "initComponent");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_press:
                finish();
                break;
            case R.id.bt_open_dialog:
                if (mBottomDialog != null) {
                    mBottomDialog.show(getSupportFragmentManager(), TAG);
                } else {
                    mBottomDialog = new BottomDialog(mContext, myData);
                    mBottomDialog.show(getSupportFragmentManager(), TAG);
                    mBottomDialog.setListenerClose(new BottomDialog.OnCloseModal() {
                        @Override
                        public void onCloseModal() {

                        }
                    });
                }

                break;
        }
    }
}
