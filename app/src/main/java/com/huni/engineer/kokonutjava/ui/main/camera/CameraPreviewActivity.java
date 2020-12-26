package com.huni.engineer.kokonutjava.ui.main.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.huni.engineer.kokonutjava.KokonutSettings;
import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.proto.JSFoodInfo;
import com.huni.engineer.kokonutjava.proto.JSUtil;
import com.huni.engineer.kokonutjava.proto.response.JSresponseAnalyze;
import com.huni.engineer.kokonutjava.proto.response.JSresponseLoginInfo;
import com.huni.engineer.kokonutjava.proto.retrofit.NetworkHelper;
import com.huni.engineer.kokonutjava.ui.popup.LoadingActivity;
import com.huni.engineer.kokonutjava.utils.DisplayUtil;
import com.huni.engineer.kokonutjava.utils.GlideUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class CameraPreviewActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = CameraPreviewActivity.class.getSimpleName();

    public static final String EXTRA_PATH = TAG + ".path";

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

        Log.d(TAG, "check - " + mContentPath);

        initComponent();
    }

    private void initComponent() {
        iv_picture = (PhotoView) findViewById(R.id.iv_picture);
        tv_bottom_cancel = (TextView) findViewById(R.id.tv_bottom_cancel);
        tv_bottom_cancel.setOnClickListener(this);
        tv_bottom_confirm = (TextView) findViewById(R.id.tv_bottom_confirm);
        tv_bottom_confirm.setOnClickListener(this);
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
                LoadingActivity.show(mContext, "onClick - tv_bottom_confirm");

                File uploadFile = new File(mContentPath);

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), uploadFile);

                Log.d(TAG, "tv_bottom_confirm/filePath : " + uploadFile.getAbsolutePath());
                Log.d(TAG, "tv_bottom_confirm/fileLength : " + uploadFile.length());

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("image", uploadFile.getName(), requestFile);

                Call<JSresponseAnalyze> resultAnalyze
                        = NetworkHelper.getInstance(mContext).getApiService().analyze(
                                mContext.getResources().getString(R.string.login_id),
                                KokonutSettings.getInstance(mContext).getSessionKey(),
                        body);

                resultAnalyze.enqueue(new Callback<JSresponseAnalyze>() {
                    @Override
                    public void onResponse(Call<JSresponseAnalyze> call, Response<JSresponseAnalyze> response) {
                            Log.d(TAG, "response - " + response.code());
                            LoadingActivity.hide(mContext, "onResponse");

                            switch (response.code()) {
                                case 200:
                                    JSresponseAnalyze result = response.body();
                                    Log.d(TAG, "onResponse/200 -  : " + result.toString());

                                    Intent intent = new Intent();
                                    intent.setClass(mContext, CameraResultActivity.class);
                                    intent.putExtra(EXTRA_PATH, mContentPath);
                                    intent.putExtra("info", JSUtil.json2String(result));
                                    startActivity(intent);

                                    break;

                                case 500:
                                    Log.e(TAG, "500!!");

                                    finish();
                                    break;
                            }
                    }

                    @Override
                    public void onFailure(Call<JSresponseAnalyze> call, Throwable t) {
                        Log.d(TAG, "onFailure/reason : " + t.getMessage());

                        Toast.makeText(mContext, "사진을 검색할 수 없습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();

                        LoadingActivity.hide(mContext, "onFailure");
                    }
                });


                break;


        }
    }
}
