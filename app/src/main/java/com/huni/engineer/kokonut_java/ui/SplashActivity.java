package com.huni.engineer.kokonut_java.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.huni.engineer.kokonut_java.KokonutDefine;
import com.huni.engineer.kokonut_java.KokonutSettings;
import com.huni.engineer.kokonut_java.R;
import com.huni.engineer.kokonut_java.common.PermissionManager;
import com.huni.engineer.kokonut_java.ui.main.MainTabActivity;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private Handler mHandler;
    private Context mContext;
    private KokonutSettings mSettings;

//    private Button test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_splash);

        mContext = this;
        mHandler = new Handler();

        mSettings = KokonutSettings.getInstance(mContext);


//        test = (Button) findViewById(R.id.bt_next_step);
//        test.setOnClickListener(this);

        initPermissionCheck();
    }

    private void initPermissionCheck() {
        Log.d(TAG, "initPermissionCheck");

        /*** 권한 체크 *******************/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !PermissionManager.getInstance(mContext).checkAllPermissionsGranted("onCreate")) {
            //권한 체크 단계 수행
            initPermission();
            return;
        }

        startKokonutApp();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initPermission() {
        Log.d(TAG, "initPermission()");

        // 최초실행이 아닌 경우
        boolean rationaleFlag = false;
        if (mSettings.getAlreadyStarted()) {
            for (String permission : PermissionManager.DANGEROUS_PERMISSIONS) {
                if (!shouldShowRequestPermissionRationale(permission)) {
                    rationaleFlag = true;
                    break;
                }
            }
        }
        showPermissionPopup(rationaleFlag);

        return;
    }

    private void showPermissionPopup(final boolean rationaleFlag) {
        Log.d(TAG, "showPermissionPopup()");

        if (PermissionManager.getInstance(mContext).isSupportRequestPermission() && !rationaleFlag) {
            PermissionManager.getInstance(mContext).requestPermissions(SplashActivity.this, KokonutDefine.REQ_APP_PERM_AGREE);
        } else {
            PermissionManager.goToAppDetailsSettings(SplashActivity.this);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        if (permissions.length > 0 && grantResults.length > 0) {
            if (PermissionManager.getInstance(mContext).checkAllPermissionsGranted(permissions, "onRequestPermissionsResult")) {
                startKokonutApp();
            } else {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED
                            && !shouldShowRequestPermissionRationale(permissions[i])) {
                        // 최초 실행
                        if (!mSettings.getAlreadyStarted()) {
                            mSettings.setAlreadyStarted(true);
                            break;
                        }
                    }
                }
                //권한 체크 단계 다시 수행
                initPermission();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.bt_next_step:
//                Intent intent = new Intent();
//                intent.setClass(mContext, MainTabActivity.class);
//
//                startActivity(intent);
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG, "onActivityResult() - requestCode: " + requestCode + ", resultCode: " + resultCode + ", " + data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == KokonutDefine.REQ_APP_DETAILS_SETTINGS) {
            if (!PermissionManager.getInstance(mContext).checkAllPermissionsGranted("onActivityResult")) {
                initPermission();
                return;
            }
            startKokonutApp();
        }
    }

    private void startKokonutApp() {
        Log.d(TAG, "startKokonutApp");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(mContext, MainTabActivity.class);
                startActivity(intent);
            }
        }, 1000);
    }
}
