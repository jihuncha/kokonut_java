package com.huni.engineer.kokonutjava.ui.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.huni.engineer.kokonutjava.R;


public class CalendarFragment extends BaseTabFragment {
    public final String TAG = CalendarFragment.class.getSimpleName();


    public CalendarFragment(Activity activity, LayoutInflater inflater) {
        super(activity, inflater, R.layout.fragment_calendar);

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

    private void initView(View root) {

    }
}
