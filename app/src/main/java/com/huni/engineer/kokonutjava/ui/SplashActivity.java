package com.huni.engineer.kokonutjava.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.huni.engineer.kokonutjava.KokonutDefine;
import com.huni.engineer.kokonutjava.KokonutSettings;
import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.common.PermissionManager;
import com.huni.engineer.kokonutjava.proto.JSLoginInfo;
import com.huni.engineer.kokonutjava.proto.response.JSresponseLoginInfo;
import com.huni.engineer.kokonutjava.proto.retrofit.NetworkHelper;
import com.huni.engineer.kokonutjava.ui.main.MainTabActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private Handler mHandler;
    private Context mContext;
    private KokonutSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_splash);

        mContext = this;
        mHandler = new Handler();

        mSettings = KokonutSettings.getInstance(mContext);

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

        requestLoginInfo();
    }

    public void requestLoginInfo() {
        JSLoginInfo myInfo = new JSLoginInfo();
        myInfo.setLoginId( mContext.getResources().getString(R.string.login_id));
        myInfo.setLoginPassword(mContext.getResources().getString(R.string.login_pass));

        Call<JSresponseLoginInfo> startLogin = NetworkHelper.getInstance(mContext).getApiService().login(myInfo);
        startLogin.enqueue(new Callback<JSresponseLoginInfo>() {
            @Override
            public void onResponse(Call<JSresponseLoginInfo> call, Response<JSresponseLoginInfo> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse - success!!");
                    JSresponseLoginInfo result = response.body();

                    KokonutSettings.getInstance(mContext).setSessionKey(result.getSessionKey());
                } else {
                    Log.e(TAG, "onResponse - error!");

                    Toast.makeText(mContext,"Login Info Not Correct!!", Toast.LENGTH_SHORT).show();
                }

                startKokonutApp();
            }

            @Override
            public void onFailure(Call<JSresponseLoginInfo> call, Throwable t) {
                Log.e(TAG, "onFailure - " + t.toString());

                Toast.makeText(mContext,"Login Info Not Correct!! - " + t.getMessage(), Toast.LENGTH_SHORT).show();

                startKokonutApp();
            }
        });
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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 1000);
    }
}
