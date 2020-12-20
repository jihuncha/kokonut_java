package com.huni.engineer.kokonutjava.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.internal.$Gson$Preconditions;
import com.huni.engineer.kokonutjava.KokonutDefine;
import com.huni.engineer.kokonutjava.KokonutSettings;
import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.common.PermissionManager;
import com.huni.engineer.kokonutjava.proto.JSLoginInfo;
import com.huni.engineer.kokonutjava.proto.JSUtil;
import com.huni.engineer.kokonutjava.proto.response.JSresponseLoginInfo;
import com.huni.engineer.kokonutjava.proto.retrofit.NetworkHelper;
import com.huni.engineer.kokonutjava.ui.main.MainTabActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        requestLoginInfo();

//        startKokonutApp();
    }

    public void requestLoginInfo() {
        JSLoginInfo test23 = new JSLoginInfo();
        test23.setLoginId( mContext.getResources().getString(R.string.login_id));
        test23.setLoginPassword(mContext.getResources().getString(R.string.login_pass));

//        Call<ResponseBody> test2 = NetworkHelper.getInstance(mContext).getApiService().login(test23);
//        test2.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d(TAG, "test : " + response.body().toString());
//
////                Log.d(TAG, "ddd : " + JSUtil.json2String(response.body()));
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

        Call<JSresponseLoginInfo> test = NetworkHelper.getInstance(mContext).getApiService().login(test23);
        test.enqueue(new Callback<JSresponseLoginInfo>() {
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

                Toast.makeText(mContext,"Login Info Not Correct!!", Toast.LENGTH_SHORT).show();

                startKokonutApp();
            }
        });

//        test.enqueue(new Callback<JSresponseLoginInfo>() {
//            @Override
//            public void onResponse(Call<JSresponseLoginInfo> call, Response<JSresponseLoginInfo> response) {
//                Log.d(TAG, "testset ? : " + response.toString());
////                Log.d(TAG, "tge : " + JSUtil.json2Object(response.body(), ))
//                if(response.isSuccessful()) {
//                    Log.d(TAG, "re : " +response.code());
//                    Log.d(TAG, "good : " + response.body());
//                    Log.d(TAG, "me : " + response.message());
//                    JSresponseLoginInfo result = response.body();
//                    Log.d(TAG, "result = " + result.toString());
//                    Log.d(TAG, " fdsf : " + result.getSessionKey());
//
//                }
//
//                startKokonutApp();
//            }
//
//            @Override
//            public void onFailure(Call<JSresponseLoginInfo> call, Throwable t) {
//                Log.e(TAG, "onFailure - " + t.getMessage());
//
//                Toast.makeText(mContext,"Login Info Not Correct!!", Toast.LENGTH_SHORT).show();
//
//                startKokonutApp();
//            }
//        });
//        test.enqueue(new Callback<JSLoginInfo>() {
//            @Override
//            public void onResponse(Call<JSLoginInfo> call, Response<JSLoginInfo> response) {
//                if(response.isSuccessful()) {
//                    Log.d(TAG, "good : " + response.body().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JSLoginInfo> call, Throwable t) {
//
//            }
//        });
//        Callback<JSresponseLoginInfo> test2 = new Callback<JSresponseLoginInfo>() {
//            @Override
//            public void onResponse(Call<JSresponseLoginInfo> call, Response<JSresponseLoginInfo> response) {
//                Log.d(TAG, "test234 : " + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<JSresponseLoginInfo> call, Throwable t) {
//
//            }
//        }
//
//        test.enqueue(test2);
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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 1000);
    }
}
