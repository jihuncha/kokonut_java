package com.huni.engineer.kokonutjava.common;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import androidx.core.content.PermissionChecker;

import com.huni.engineer.kokonutjava.KokonutDefine;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {
	public static final String LOG_TAG = PermissionManager.class.getSimpleName();
	
	public static final String[] DANGEROUS_PERMISSIONS = {
			/*Manifest.permission.INSTALL_PACKAGES,*/
			/*위치*/
			//Manifest.permission.ACCESS_FINE_LOCATION,
			//Manifest.permission.ACCESS_COARSE_LOCATION,
//			Manifest.permission.READ_CONTACTS,
//			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.RECORD_AUDIO,
			/*Manifest.permission.RECEIVE_SMS,*/
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.CAMERA,
			//Manifest.permission.CALL_PHONE
	};

	public static final String[] CAMERA_PERMISSIONS = {
			Manifest.permission.CAMERA,
			Manifest.permission.RECORD_AUDIO
	};
	
	/*
	 * 멤버 변수
	 */
	private Context mContext = null;
	private int     mTargetSdkVersion = Build.VERSION_CODES.M;
	private static 	PermissionManager sInstance;
	
	
	public static PermissionManager getInstance(Context context) {
		synchronized (PermissionManager.class) {
			if (sInstance == null) {
				sInstance = new PermissionManager(context);
			}
		}
		return sInstance;
	}
	
	private PermissionManager(Context context) {
		mContext = context;
		mTargetSdkVersion = Build.VERSION_CODES.M;
		try {
			final PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
			mTargetSdkVersion = info.applicationInfo.targetSdkVersion;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 앱 전체권한에 대해 동의가 되어있는지 확인하는 함수
	 *
	 * @return all granted : true, other : false
	 */
	@TargetApi(Build.VERSION_CODES.M)
	public boolean checkAllPermissionsGranted(String f) {
		return checkAllPermissionsGranted(DANGEROUS_PERMISSIONS, f);
	}

	/**
	 * 앱 전체권한에 대해 동의가 되어있는지 확인하는 함수
	 *
	 * @return all granted : true, other : false
	 */
	@TargetApi(Build.VERSION_CODES.M)
	public boolean checkCameraPermissionsGranted(String f) {
		return checkAllPermissionsGranted(CAMERA_PERMISSIONS, f);
	}

	@TargetApi(Build.VERSION_CODES.M)
	public boolean checkAllPermissionsGranted(String[] permissions, String f) {
		if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			for (String permission : permissions) {
				if (!isPermissionGranted(permission)) {
					Log.d(LOG_TAG, "checkAllPermissionsGranted() - f: " + f + ", result: " + false);
					return false;
				}
			}
		}
		Log.d(LOG_TAG, "checkAllPermissionsGranted() - f: " + f + ", result: " + true);

		return true;
	}

	public boolean isSupportRequestPermission() {
        return mTargetSdkVersion >= Build.VERSION_CODES.M;
    }

	@TargetApi(Build.VERSION_CODES.M)
	public void requestPermissions(Activity activity, int reqCode) {
		List<String> deniedList = new ArrayList<String>();
		for (String permission : DANGEROUS_PERMISSIONS) {
			if (!isPermissionGranted(permission)) {
				deniedList.add(permission);
			}
		}

		activity.requestPermissions(deniedList.toArray(new String[deniedList.size()]), reqCode);
	}

	@TargetApi(Build.VERSION_CODES.M)
	public void requestPermissionsCamera(Activity activity, int reqCode) {
		List<String> deniedList = new ArrayList<String>();
		for (String permission : CAMERA_PERMISSIONS) {
			if (!isPermissionGranted(permission)) {
				deniedList.add(permission);
			}
		}

		activity.requestPermissions(deniedList.toArray(new String[deniedList.size()]), reqCode);
	}


	@TargetApi(Build.VERSION_CODES.M)
	public boolean isPermissionGranted(String permission) {
		boolean result = true;
		if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (mTargetSdkVersion >= Build.VERSION_CODES.M) {
				result = mContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
				Log.d(LOG_TAG, "isPermissionGranted(1) - mTargetSdkVersion: " + mTargetSdkVersion + ", " + permission + ": " + result);
			}
			else {
				result = PermissionChecker.checkSelfPermission(mContext, permission) == PermissionChecker.PERMISSION_GRANTED;
				Log.d(LOG_TAG, "isPermissionGranted(2) - mTargetSdkVersion: " + mTargetSdkVersion + ", " + permission + ": " + result);
			}
		}

		return result;
	}

	// 접근성 권한이 있는지 없는지 확인하는 부분
	// 있으면 true, 없으면 false
	public boolean checkAccessibilityPermissions() {
		AccessibilityManager accessibilityManager = (AccessibilityManager) mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);

		// getEnabledAccessibilityServiceList는 현재 접근성 권한을 가진 리스트를 가져오게 된다
		List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.DEFAULT);

		for (int i = 0; i < list.size(); i++) {
			AccessibilityServiceInfo info = list.get(i);
			Log.d(LOG_TAG, "checkAccessibilityPermissions() - info: " + info.getResolveInfo().toString());

			// 접근성 권한을 가진 앱의 패키지 네임과 패키지 네임이 같으면 현재앱이 접근성 권한을 가지고 있다고 판단함
			if (info.getResolveInfo().serviceInfo.packageName.equals(mContext.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 앱 상세 설정으로 이동
	 * @param activity
	 */
	public static void goToAppDetailsSettings(Activity activity) {
		Log.d(LOG_TAG, "goToAppDetailsSettings(ENTER)");

		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
		activity.startActivityForResult(intent, KokonutDefine.REQ_APP_DETAILS_SETTINGS);

		Log.d(LOG_TAG, "goToAppDetailsSettings(LEAVE)");
	}
}
