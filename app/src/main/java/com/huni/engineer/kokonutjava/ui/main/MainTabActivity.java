package com.huni.engineer.kokonutjava.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.huni.engineer.kokonutjava.KokonutSettings;
import com.huni.engineer.kokonutjava.R;

public class MainTabActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = MainTabActivity.class.getSimpleName();

    /**
     * Tab 의 index 정보
     */
    public static final int MAIN_TAB_CAMERA = 0; // 카메라 탭
    public static final int MAIN_TAB_SEARCH = 1; // 탐색기 탭
    public static final int MAIN_TAB_CALENDAR = 2; // 달력 탭
    public static final int TOTAL_TAB_CNT = MAIN_TAB_CALENDAR + 1;

    private Activity mActivity;
    private Context mContext;
    private BaseTabFragment[] mTabFragments = new BaseTabFragment[TOTAL_TAB_CNT];

    /**
     * 현재 선택한 Tab 의 정보
     */
    private int mCurrentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        mContext = this;

        Log.d(TAG, "test : " + KokonutSettings.getInstance(mContext).getSessionKey());

        //initialize the tabs...
        mTabFragments[MAIN_TAB_CAMERA] = new CameraFragment(this, getLayoutInflater());
        mTabFragments[MAIN_TAB_SEARCH] = new SearchFragment(this, getLayoutInflater());
        mTabFragments[MAIN_TAB_CALENDAR] = new CalendarFragment(this, getLayoutInflater());

        initTabnViewPager();
    }

    /**
     * initialize tab & viewpager layout
     */
    private ViewPager mViewPager;
    private TabLayout mTab;
    private MainTabViewPagerAdapter mViewPagerAdater;

    private int getTabIndex2Id(int tabIndex) {
        return tabIndex;
    }

    private BaseTabFragment getCurrentFragment() {
        return mTabFragments[mCurrentTab];
    }

    private void initTabnViewPager() {
        Log.d(TAG, "initTabnViewPager()");

        //ViewPager
        if (mViewPager == null) {
            mViewPager = (ViewPager) findViewById(R.id.tab_view_pager);
            mViewPager.setOffscreenPageLimit(2);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //Log.d(TAG, "onPageScrolled E. position : " + position + " , positionOffset : " + positionOffset + ", positionOffsetPixels : " + positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    Log.d(TAG, "onPageSelected() - selected position : " + position);
                    mCurrentTab = getTabIndex2Id(position);
//                    updateView(PTTDefine.TOUCH_REASON.I_UI_SELECTED, "onPageSelected");
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.d(TAG, "onPageScrollStateChanged() - state : " + state);
                }
            });
        }

        // Set up the ViewPager with the sections adapter.
        mViewPagerAdater = new MainTabViewPagerAdapter();
        mViewPagerAdater.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d(TAG, "DataSetObserver. onChanged");
                // http://i5on9i.blogspot.com/2014/01/viewpager-refresh.html
                mViewPagerAdater.notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
                Log.d(TAG, "DataSetObserver. onInvalidated");
            }
        });
        mViewPager.setAdapter(mViewPagerAdater);

        //Tab
        mTab = (TabLayout) findViewById(R.id.tabs_layout);
        mTab.setupWithViewPager(mViewPager);
        for (int i = 0; i < getTabCount(); i++) {
            int resID = getIcon(getTabIndex2Id(i));
            TabLayout.Tab tab = mTab.getTabAt(i);
            tab.setCustomView(R.layout.tab_item_layout);
            ImageView iv = (ImageView) (tab.getCustomView().findViewById(R.id.tab_icon));
            iv.setImageResource(resID);

            Log.d(TAG, "[0] tab: " + tab.hashCode() + ", view: " + tab.getCustomView());
        }

        mViewPager.setCurrentItem(getTabId2Index(mCurrentTab));
    }

    private int getIcon(int tabIndex) {
        switch (tabIndex) {
            case MAIN_TAB_CAMERA        : return R.drawable.navi_1;
            case MAIN_TAB_SEARCH        : return R.drawable.navi_2;
            case MAIN_TAB_CALENDAR      : return R.drawable.navi_3;
        }

        return R.drawable.icon_todo;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 모든 fragment 에 대한 update
     */
//    private void updateView(int reason, String f) {
//        Log.d(TAG, "updateView() - f: " + f + ", reason: " + PTTDefine.TOUCH_REASON.valueOf(reason));
//
//        switch (reason) {
//            if (reason != PTTDefine.TOUCH_REASON.I_UI_SELECTED
//                    && reason != PTTDefine.TOUCH_REASON.I_UI_SELECTED_BY_USER) {
//                Log.d(TAG, "reason != PTTDefine.TOUCH_REASON.I_UI_SELECTED");
//                for (int i = 0; i < TOTAL_TAB_CNT; i++) {
//                    mTabFragments[i].update(reason);
//                }
//            }
//        }
//    }

    /**
     * View Pager Adapter
     */
    public class MainTabViewPagerAdapter extends PagerAdapter {
        public MainTabViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Log.d(TAG, "instantiateItem() - position: " + position);
            View view = mTabFragments[getTabIndex2Id(position)].getView();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d(TAG, "destroyItem() - position: " + position);
            container.removeView((View) object);
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * page가 몇개인지를 return 함
         *
         * @return
         */
        @Override
        public int getCount() {
            return 3;
        }
    }

    private int getTabCount() {
        return 3;
    }

    private int getTabId2Index(int tabId) {
        return tabId;
    }
}