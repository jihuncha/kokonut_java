package com.huni.engineer.kokonut_java.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.huni.engineer.kokonut_java.R;
import com.huni.engineer.kokonut_java.ui.main.MainTabActivity;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = SplashActivity.class.getSimpleName();

    private Handler mHandler;
    private Context mContext;

    private Button test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_splash);

        mContext = this;
        mHandler = new Handler();

        test = (Button) findViewById(R.id.bt_next_step);
        test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next_step:
                Intent intent = new Intent();
                intent.setClass(mContext, MainTabActivity.class);

                startActivity(intent);
                break;
        }
    }
}
