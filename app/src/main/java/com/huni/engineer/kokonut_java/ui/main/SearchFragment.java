package com.huni.engineer.kokonut_java.ui.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.huni.engineer.kokonut_java.R;


public class SearchFragment extends BaseTabFragment {
    public final String TAG = SearchFragment.class.getSimpleName();


    public SearchFragment(Activity activity, LayoutInflater inflater) {
        super(activity, inflater, R.layout.fragment_search);

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
