package com.huni.engineer.kokonutjava.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class DisplayUtil {
	public static final String TAG = DisplayUtil.class.getSimpleName();

	public static int convertPixelToDip(Context context, int pixel) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pixel / scale);
	}

	public static int convertDipToPixel(Context context, int DP) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (DP * scale);
	}

	public static int convertDipToPixel(Context context, float DP) {
		float scale = context.getResources().getDisplayMetrics().density;
//		return (int) (DP * scale);
		return Math.round(DP * scale);	// 반올림 처리. 
	}

	public static int DpToPixel(Context context, float DP) {
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DP, context.getResources()
				.getDisplayMetrics());
		return (int) px;
	}

	public static int convertDimensToPixel(Context context, int dimens) {
		return context.getResources().getDimensionPixelSize(dimens);
	}

	public static int getDensityDpi(Context context) {
		return context.getResources().getDisplayMetrics().densityDpi;
	}

	public static boolean isHighDpi(Context context) {
		int density = context.getResources().getDisplayMetrics().densityDpi;
		switch (density) {
			case DisplayMetrics.DENSITY_LOW:
				Log.d(TAG, "isHighDpi() - DENSITY_LOW(" + density + ") : false");
				return false;

			case DisplayMetrics.DENSITY_MEDIUM:
				Log.d(TAG, "isHighDpi() - DENSITY_MEDIUM(" + density + ") : false");
				return false;

			case 150: //모냐??
				Log.d(TAG, "isHighDpi() - DENSITY_MEDIUM(" + density + ") : false");
				return false;

			case DisplayMetrics.DENSITY_HIGH:
				Log.d(TAG, "isHighDpi() - DENSITY_HIGH(" + density + ") : true");
				return true;

			case DisplayMetrics.DENSITY_XHIGH:
				Log.d(TAG, "isHighDpi() - DENSITY_XHIGH(" + density + ") : true");
				return true;

			case DisplayMetrics.DENSITY_XXHIGH:
				Log.d(TAG, "isHighDpi() - DENSITY_XXHIGH(" + density + ") : true");
				return true;

			case DisplayMetrics.DENSITY_XXXHIGH:
				Log.d(TAG, "isHighDpi() - DENSITY_XXXHIGH(" + density + ") : true");
				return true;

		}
		Log.d(TAG, "isHighDpi() - DENSITY_????(" + density + ") : true");

		return true;
	}

	public static int getScreenWidth(Context context) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		
		return displayMetrics.widthPixels;
	}

	public static int getScreenHeight(Context context) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		
		return displayMetrics.heightPixels;
	}

	public static void setStatusBarWithTransparent(Activity a, int color) {
		if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
			setWindowFlag(a, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
		}
		if (Build.VERSION.SDK_INT >= 19) {
			a.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}
		if (Build.VERSION.SDK_INT >= 21) {
			setWindowFlag(a, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
			a.getWindow().setStatusBarColor(color);
		}
	}

	public static void setWindowFlag(Activity activity, final int bits, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
