package com.huni.engineer.kokonutjava.ui.main.camera;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.huni.engineer.kokonutjava.R;

public class CameraPreviewActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_capture);

//        mContext = this;
//        mHandler = new Handler();
//
//        initComponent();
    }

    @Override
    public void onClick(View view) {

    }
}
