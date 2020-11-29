package com.huni.engineer.kokonut_java.ui.main;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.huni.engineer.kokonut_java.R;
import com.huni.engineer.kokonut_java.ui.utils.AppBarStateChangeListener;
import com.huni.engineer.kokonut_java.ui.utils.SwipeViewPager;


public class CameraFragment extends BaseTabFragment {
    public final String TAG = CameraFragment.class.getSimpleName();


    public CameraFragment(Activity activity, LayoutInflater inflater) {
        super(activity, inflater, R.layout.fragment_camera);

        // Inflate the layout for this fragment
        initView(mMyView);
    }

    public String TAG() {
        return TAG;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private CoordinatorLayout cl_parent_view;
    private AppBarLayout appbar;
    private CollapsingToolbarLayout ctl_container;
    private Toolbar toolbar;

    private TextView tv_camera_title;
    private NestedScrollView nsv_scroll_view;
    private SwipeViewPager svp_view_pager;

    private TextView tv_camera_title_toolbar;
    private TextView tv_camera_date_toolbar;

    private void initView(View root) {
        cl_parent_view = (CoordinatorLayout) root.findViewById(R.id.cl_parent_view);

        appbar = (AppBarLayout) root.findViewById(R.id.appbar);
        ctl_container = (CollapsingToolbarLayout) root.findViewById(R.id.ctl_container);

        toolbar = (Toolbar) root.findViewById(R.id.toolbar);

        tv_camera_title = (TextView) root.findViewById(R.id.tv_camera_title);

        nsv_scroll_view = (NestedScrollView) root.findViewById(R.id.nsv_scroll_view);

        svp_view_pager = (SwipeViewPager) root.findViewById(R.id.svp_view_pager);

        tv_camera_title_toolbar = (TextView) root.findViewById(R.id.tv_camera_title_toolbar);
        tv_camera_date_toolbar = (TextView) root.findViewById(R.id.tv_camera_date_toolbar);

        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d(TAG, "test : " + state.name());

                if (state == State.EXPANDED) {
                    Log.d(TAG, "??");
                    tv_camera_title_toolbar.setVisibility(View.GONE);
                    tv_camera_date_toolbar.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED){
                    Log.d(TAG, "!!!");
                    tv_camera_title_toolbar.setVisibility(View.VISIBLE);
                    tv_camera_date_toolbar.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}
