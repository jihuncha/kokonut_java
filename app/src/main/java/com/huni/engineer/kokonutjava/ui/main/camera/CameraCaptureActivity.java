package com.huni.engineer.kokonutjava.ui.main.camera;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.ui.utils.CameraSettings;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.controls.PictureFormat;
import com.otaliastudios.cameraview.controls.Preview;
import com.otaliastudios.cameraview.gesture.Gesture;
import com.otaliastudios.cameraview.gesture.GestureAction;
import com.otaliastudios.cameraview.markers.DefaultAutoFocusMarker;
import com.otaliastudios.cameraview.size.Size;
import com.otaliastudios.cameraview.size.SizeSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CameraCaptureActivity extends AppCompatActivity {
    private final String TAG = CameraCaptureActivity.class.getSimpleName();

    private CameraView mCameraView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_capture);

        initComponent();
    }

    private void initComponent() {
        mCameraView = findViewById(R.id.camera_view);
        mCameraView.setLifecycleOwner(this);
        mCameraView.setPictureSize(mSizeSelector);
        mCameraView.setVideoSize(mSizeSelector);
        mCameraView.setPictureMetering(true); // Meter before takePicture()
        mCameraView.setAutoFocusMarker(new DefaultAutoFocusMarker());
        mCameraView.mapGesture(Gesture.PINCH, GestureAction.ZOOM); // Pinch to zoom!
        mCameraView.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS); // Tap to focus!
        mCameraView.mapGesture(Gesture.LONG_TAP, GestureAction.TAKE_PICTURE); // Long tap to shoot!
        mCameraView.setPreview(Preview.TEXTURE);
        mCameraView.mapGesture(Gesture.SCROLL_VERTICAL, GestureAction.EXPOSURE_CORRECTION);

//        mCameraView.setMode(Mode.PICTURE);
//        mCameraView.setPictureFormat(PictureFormat.JPEG);
//        mCameraView.takePicture();

        mCameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                Log.d(TAG, "onPictureTaken() - result.size: " + result.getSize() + ", width: " + result.getSize().getWidth() + ", height: " + result.getSize().getHeight());
//                PopupManager.getInstance(mContext).showToast("size: " + result.getSize());

//                showLoading(true, "onPictureTaken");
//                result.toFile(new File(mCapturePath), new FileCallback() {
//                    @Override
//                    public void onFileReady(@Nullable File file) {
////                        hideLoading("onPictureTaken.onFileReady");
////                        updateStep(STEP_PREVIEW, "onPictureTaken");
//
//                        //ImageCropActivity.startCrop(CameraActivity.this, mCapturePath, 1);
//                    }
//                });
            }

            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                Log.d(TAG, "onVideoTaken() - result: " + result);
            }

            @Override
            public void onAutoFocusStart(@NonNull PointF point) {
                Log.d(TAG, "onAutoFocusStart()");
                // Touch metering was started by a gesture or by startAutoFocus(float, float).
                // The camera is currently trying to meter around that area.
                // This can be used to draw things on screen.
            }

            @Override
            public void onAutoFocusEnd(boolean successful, @NonNull PointF point) {
                Log.d(TAG, "onAutoFocusEnd()");
                // Touch metering operation just ended. If successful, the camera will have converged
                // to a new focus point, and possibly new exposure and white balance as well.
                // The point is the same that was passed to onAutoFocusStart.
            }
        });
    }

    int mSelectedSize = CameraSettings.Resolution.I_1080p;
    SizeSelector mSizeSelector = new SizeSelector() {
        @NonNull
        @Override
        public List<Size> select(@NonNull List<Size> source) {
            Log.d(TAG, "select() - source: " + source);

            final int width = CameraSettings.Resolution.getWidth(mSelectedSize);
            final int height= CameraSettings.Resolution.getHeight(mSelectedSize);
            List<Size> selected = new ArrayList<>();

            Size biggest  = null;
            Size smallest = null;
            for (Size size : source) {
                //check biggest
                if (biggest == null) {
                    biggest = size;
                }
                else if (biggest.compareTo(size) < 0) {
                    biggest = size;
                }

                //check smallest
                if (smallest == null) {
                    smallest = size;
                }
                else if (smallest.compareTo(size) > 0) {
                    smallest = size;
                }

                //check width
                if (mSelectedSize != CameraSettings.Resolution.I_MAX
                        && mSelectedSize != CameraSettings.Resolution.I_MIN)
                {
                    if (width == size.getWidth()) {
                        selected.add(size);
                    }
                    else if (height == size.getHeight()) {
                        selected.add(size);
                    }
                }
            }
            switch (mSelectedSize) {
                case CameraSettings.Resolution.I_MIN:
                    if (smallest != null) {
                        selected.add(smallest);
                    }
                    break;

                case CameraSettings.Resolution.I_MAX:
                default:
                    if (biggest != null) {
                        selected.add(biggest);
                    }
                    break;
            }

            Log.d(TAG, "select() - selected: " + sortBy(selected));

            return selected;
        }
    };

    private List<Size> sortBy(List<Size> selected) {
        final int width  = CameraSettings.Resolution.getWidth(mSelectedSize);
        final int height = CameraSettings.Resolution.getHeight(mSelectedSize);
        Comparator<Size> comparator = new Comparator<Size>() {
            @Override
            public int compare(Size v1, Size v2) {
                //width가 근접한
                int widthV1 = Math.abs(width - v1.getWidth());
                int widthV2 = Math.abs(width - v2.getWidth());
                if (widthV1 == widthV2) {
                    int heightV1 = Math.abs(height - v1.getHeight());
                    int heightV2 = Math.abs(height - v2.getHeight());

                    return heightV1 - heightV2;
                }

                return widthV1 - widthV2;
            }
        };
        Collections.sort(selected, comparator);

        return selected;
    }


}
