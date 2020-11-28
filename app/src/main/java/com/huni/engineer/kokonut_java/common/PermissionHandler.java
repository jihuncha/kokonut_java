//package com.huni.engineer.kokonut_java.common;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.provider.Settings;
//import android.util.Log;
//
//import androidx.core.content.PermissionChecker;
//
//
//import com.huni.engineer.kokonut_java.R;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class PermissionHandler {
//    public static final String TAG = PermissionHandler.class.getSimpleName();
//
//    public static final int REQ_APP_PERM_AGREE_CAMERA     = 1103;
//    public static final int REQ_APP_PERM_AGREE_STORAGE    = 1107;
//
//    public static final int REQ_CAMERA    = REQ_APP_PERM_AGREE_CAMERA;
//    public static final int REQ_STORAGE   = REQ_APP_PERM_AGREE_STORAGE;
//
//    private Activity activity;
//    private String[] permissions;
//    private int      requestId;
//
//    private boolean finishFlag;
//
//    private boolean rationaleFlag = false;
//    private OnPopupCancelListener listener = null;
//
//    public interface OnPopupCancelListener {
//        public void onPopupCancel();
//    }
//
//    public void setOnPopupCancelListener(OnPopupCancelListener listener) {
//        this.listener = listener;
//    }
//
//    public PermissionHandler(Activity activity, int requestId, boolean finishFlag) {
//        this.activity    = activity;
//        this.requestId   = requestId;
//        this.finishFlag  = finishFlag;
//        this.permissions = toPermissions();
//    }
//
//    public boolean checkPermissions(boolean showPopup, String f) {
//        Log.d(TAG, "checkPermissions() - f: " + f + ", showPopup: " + showPopup);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        rationaleFlag = false;
//        List<String> deniedList = new ArrayList<String>();
//        for (String permission : permissions) {
//            boolean isGranted = isPermissionGranted(activity, permission);
//            boolean shouldShowRationale = (!isGranted && activity.shouldShowRequestPermissionRationale(permission));
//            boolean alreadyChecked = PTTSettings.getInstance(activity).getAlreadyPermissionChecked(permission);
//            Log.d(TAG, "checkPermissions() - permission: " + permission
//                    + ", isGranted: " + isGranted
//                    + ", shouldShowRationale: " + shouldShowRationale
//                    + ", alreadyChecked: " + alreadyChecked
//            );
//
//            if (!isGranted) {
//                deniedList.add(permission);
//            }
//            if (alreadyChecked && !shouldShowRationale) {
//                rationaleFlag = true;
//            }
//        }
//
//        Log.d(TAG, "checkPermissions() - permissions: " + Arrays.toString(permissions)
//                + ", denied: " + deniedList.size()
//                + ", showPopup: " + showPopup
//                + ", rationaleFlag: " + rationaleFlag);
//        if (deniedList.size() > 0) {
//            if (showPopup) {
//                showPermissionPopup();
//            }
//            else {
//                activity.requestPermissions(deniedList.toArray(new String[deniedList.size()]), requestId);
//                for (String permission : deniedList) {
//                    if (!PTTSettings.getInstance(activity).getAlreadyPermissionChecked(permission)) {
//                         PTTSettings.getInstance(activity).setAlreadyPermissionChecked(permission, true);
//                    }
//                }
//            }
//            return false;
//        }
//
//        return true;
//    }
//
//    public static boolean isPermissionGranted(Activity activity, String permission) {
//        boolean result = true;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            result = PermissionChecker.checkSelfPermission(activity, permission) == PermissionChecker.PERMISSION_GRANTED;
//            Log.d(TAG, "isPermissionGranted() - " + permission + ": " + result);
//        }
//
//        return result;
//    }
//    public static boolean isPermissionsGranted(Activity activity, String[] permissions) {
//        for (String permission : permissions) {
//            if (!isPermissionGranted(activity, permission)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void showPermissionPopup() {
//        Log.d(TAG, "showPermissionPopup()");
//        if (rationaleFlag) {
//            showRationalePopup();
//            return;
//        }
//        PopupManager.getInstance(activity).showDialog(
//                activity,
//                null,
//                toPopupMessage(),
//                activity.getString(R.string.cancel),
//                activity.getString(R.string.common_label_ok),
//                new CommonPopup.IPopupMsgListener() {
//                    @Override
//                    public void onNagativeClick() {
//                        //listener가 등록되어 있는 경우, listener 호출
//                        if (listener != null) {
//                            listener.onPopupCancel();
//                            return;
//                        }
//                        if (finishFlag) {
//                            activity.finish();
//                        }
//                    }
//
//                    @Override
//                    public void onPositiveClick() {
//                        checkPermissions(false, "showPermissionPopup");
//                    }
//                }
//        );
//    }
//
//    private void showRationalePopup() {
//        Log.d(TAG, "showRationalePopup()");
//        PopupManager.getInstance(activity).showDialog(
//                activity,
//                null,
//                toPopupMessage() + " " + activity.getString(R.string.permission_settings_warning),
//                activity.getString(R.string.cancel),
//                activity.getString(R.string.common_label_setting),
//                new CommonPopup.IPopupMsgListener() {
//                    @Override
//                    public void onNagativeClick() {
//                        if (finishFlag) {
//                            activity.finish();
//                        }
//                    }
//
//                    @Override
//                    public void onPositiveClick() {
//                        goToAppDetailsSettings(activity);
//                    }
//                }
//        );
//    }
//
//    public void goToAppDetailsSettings(Activity activity) {
//        Log.d(TAG, "goToAppDetailsSettings(ENTER)");
//
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
//        activity.startActivityForResult(intent, requestId);
//
//        Log.d(TAG, "goToAppDetailsSettings(LEAVE)");
//    }
//
//    private String toPopupMessage() {
//        return toPopupMessage(activity, requestId);
//    }
//
//    public static String toPopupMessage(Activity activity, int requestId) {
//        switch (requestId) {
//            case REQ_CAMERA    : return activity.getString(R.string.camera_permission_warning);
//            case REQ_STORAGE   : return activity.getString(R.string.voipcall_permission_storage);
//        }
//
//        return activity.getString(R.string.default_permission_warning);
//    }
//
//    private String[] toPermissions() {
//        return toPermissions(requestId);
//    }
//    public static String[] toPermissions(int requestId) {
//        switch (requestId) {
//            case REQ_CAMERA    : return new String[] { Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA };
//            case REQ_STORAGE   : return new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE };
//        }
//
//        return new String[] { };
//    }
//}
