package com.huni.engineer.kokonutjava.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.test.ParallelNestedScrollingActivity;
import com.huni.engineer.kokonutjava.ui.utils.AppBarStateChangeListener;
import com.huni.engineer.kokonutjava.ui.utils.SwipeViewPager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CameraFragment extends BaseTabFragment implements View.OnClickListener{
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

    private LinearLayout ll_toolbar_area;

    private TextView tv_camera_title;
    private TextView tv_camera_date_title;
    private NestedScrollView nsv_scroll_view;
    private SwipeViewPager svp_view_pager;

    private TextView tv_camera_title_toolbar;
    private TextView tv_camera_date_toolbar;

    private RecyclerView rv_date_selector;

    //뷰페이저 어댑터
    private ViewPager2 vp_bottom_container;
    private DateAdapter dateAdapter;

    private ItemAdapter itemAdapter;

    private void initView(View root) {
        cl_parent_view = (CoordinatorLayout) root.findViewById(R.id.cl_parent_view);

        appbar = (AppBarLayout) root.findViewById(R.id.appbar);

        toolbar = (Toolbar) root.findViewById(R.id.toolbar);

        ll_toolbar_area = (LinearLayout) root.findViewById(R.id.ll_toolbar_area);
        ll_toolbar_area.setOnClickListener(this);

        tv_camera_title = (TextView) root.findViewById(R.id.tv_camera_title);
        tv_camera_title.setOnClickListener(this);
        tv_camera_date_title = (TextView) root.findViewById(R.id.tv_camera_date_title);
        tv_camera_date_title.setOnClickListener(this);

//        nsv_scroll_view = (NestedScrollView) root.findViewById(R.id.nsv_scroll_view);

//        svp_view_pager = (SwipeViewPager) root.findViewById(R.id.svp_view_pager);

        tv_camera_title_toolbar = (TextView) root.findViewById(R.id.tv_camera_title_toolbar);
        tv_camera_date_toolbar = (TextView) root.findViewById(R.id.tv_camera_date_toolbar);

        rv_date_selector = (RecyclerView) root.findViewById(R.id.rv_date_selector);

        //초기화
        ll_toolbar_area.setVisibility(View.GONE);
        tv_camera_title_toolbar.setVisibility(View.GONE);
        tv_camera_date_toolbar.setVisibility(View.GONE);

        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d(TAG, "AppBarStateChangeListener/onStateChanged : " + state.name());

                if (state == State.EXPANDED) {
                    Log.d(TAG, "EXPANDED");
                    ll_toolbar_area.setVisibility(View.GONE);
                    tv_camera_title_toolbar.setVisibility(View.GONE);
                    tv_camera_date_toolbar.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED){
                    Log.d(TAG, "COLLAPSED");
                    ll_toolbar_area.setVisibility(View.VISIBLE);
                    tv_camera_title_toolbar.setVisibility(View.VISIBLE);
                    tv_camera_date_toolbar.setVisibility(View.VISIBLE);
                }
            }
        });

        vp_bottom_container = (ViewPager2) root.findViewById(R.id.vp_bottom_container);
        vp_bottom_container.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        makeCalendarView();

//        initPager();
        initPager();
    }

    //오늘 날짜 저장
    private String calendarToday;
    private List<String> calendarDateAll;
//    private List<Integer> calendarDayOfWeek;

    private void makeCalendarView() {
        Log.d(TAG, "makeCalendarView");

        DecimalFormat df = new DecimalFormat("00");
        Calendar currentCalendar = Calendar.getInstance();

        calendarToday = currentCalendar.get(Calendar.YEAR) + " / " + df.format(currentCalendar.get(Calendar.MONTH) + 1) + " / " + df.format(currentCalendar.get(Calendar.DATE));

        tv_camera_date_toolbar.setText(calendarToday);
        tv_camera_date_title.setText(calendarToday.split(" / ")[0] + " / " + calendarToday.split(" / ")[1] );

        calendarDateAll = new ArrayList<>();

        calendarDateAll.add(
                df.format(currentCalendar.get(Calendar.YEAR)) + "/" + df.format(currentCalendar.get(Calendar.MONTH) + 1) + "/" +
                df.format(currentCalendar.get(Calendar.DATE)) + "/" + returnDayOfWeek(currentCalendar.get(Calendar.DAY_OF_WEEK)));

        for (int i = 1; i < 7; i++) {
            currentCalendar.add(currentCalendar.DATE , 1);
            calendarDateAll.add(
                    df.format(currentCalendar.get(Calendar.YEAR)) + "/" + df.format(currentCalendar.get(Calendar.MONTH) + 1) + "/" +
                    df.format(currentCalendar.get(Calendar.DATE)) + "/" + returnDayOfWeek(currentCalendar.get(Calendar.DAY_OF_WEEK)));
        }

        Log.d(TAG, "calendarToday - " + calendarToday + ", CalendarDateAll - " +
                calendarDateAll.toString());

        initDateSelector();
    }

    private void initDateSelector() {
        Log.d(TAG, "initDateSelector");

        //recyclerview 설정
        LinearLayoutManager horizonalLayoutManager =
                new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);

        rv_date_selector.setLayoutManager(horizonalLayoutManager);
        rv_date_selector.setHasFixedSize(true);

        if (dateAdapter == null) {
            dateAdapter = new DateAdapter(calendarDateAll);
            rv_date_selector.setAdapter(dateAdapter);
        } else {
            dateAdapter.setData(calendarDateAll);
            dateAdapter.notifyDataSetChanged();
        }
    }

    private void initPager() {
        if (itemAdapter == null) {
            itemAdapter = new ItemAdapter();
            vp_bottom_container.setAdapter(itemAdapter);
            vp_bottom_container.registerOnPageChangeCallback(mPageChange);
        } else {
//            itemAdapter.setData(myProfileImageList);
            itemAdapter.notifyDataSetChanged();
            if (vp_bottom_container != null) {
                vp_bottom_container.unregisterOnPageChangeCallback(mPageChange);
            }
            vp_bottom_container.registerOnPageChangeCallback(mPageChange);
//            ll_pager.invalidate();
//            if (currentPosition - 1 >= 0) {
//                ll_pager.setCurrentItem(currentPosition - 1);
//            }
        }
    }

    //하단 viewpager 이동을 상단에 반영.
    ViewPager2.OnPageChangeCallback mPageChange = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected() - position: " + position);

            if (dateAdapter != null) {
                dateAdapter.setPosition(position);
                dateAdapter.notifyDataSetChanged();
            }

            String date = calendarDateAll.get(position);
            tv_camera_date_toolbar.setText(date.split("/")[0] + " / " + date.split("/")[1] + " / " +
                    date.split("/")[2]);

            super.onPageSelected(position);
        }
    };


    private String returnDayOfWeek(int inputDate) {
        Log.d(TAG, "returnDayOfWeek - " + inputDate);

        String temp = "";

        switch (inputDate) {
            case 1:
                temp = mContext.getResources().getString(R.string.camera_sunday);
                break;
            case 2:
                temp = mContext.getResources().getString(R.string.camera_monday);
                break;
            case 3:
                temp = mContext.getResources().getString(R.string.camera_thursday);
                break;
            case 4:
                temp = mContext.getResources().getString(R.string.camera_wednesday);
                break;
            case 5:
                temp = mContext.getResources().getString(R.string.camera_thursday);
                break;
            case 6:
                temp = mContext.getResources().getString(R.string.camera_friday);
                break;
            case 7:
                temp = mContext.getResources().getString(R.string.camera_saturday);
                break;
            default:
                temp = "";
                break;
        }

        return temp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_toolbar_area:
                Log.d(TAG, "onClick - ll_toolbar_area");
                if (appbar != null) {
                    appbar.setExpanded(true);
                }
                break;

            case R.id.tv_camera_title:
                Log.d(TAG, "onClick - tv_camera_title");
                if (appbar != null) {
//                    CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
//                    params.height = 3*80; // HEIGHT

//                    appbar.setLayoutParams(params);
                    appbar.setExpanded(false);

//                    appbar.getlist
                }
                break;

//            if (state == AppBarStateChangeListener.State.EXPANDED) {
//                Log.d(TAG, "EXPANDED");
//                ll_toolbar_area.setVisibility(View.GONE);
//                tv_camera_title_toolbar.setVisibility(View.GONE);
//                tv_camera_date_toolbar.setVisibility(View.GONE);
//            } else if (state == AppBarStateChangeListener.State.COLLAPSED){
//                Log.d(TAG, "COLLAPSED");
//                ll_toolbar_area.setVisibility(View.VISIBLE);
//                tv_camera_title_toolbar.setVisibility(View.VISIBLE);
//                tv_camera_date_toolbar.setVisibility(View.VISIBLE);
//            }
            case R.id.tv_camera_date_title:
//                Log.d(TAG, "!!!!!!!!!!");
//
//                Intent intent = new Intent();
//                intent.setClass(mActivity, ParallelNestedScrollingActivity.class);
//                mActivity.startActivity(intent);
                break;
        }
    }

    //상단 날짜 recyclerview
    public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ItemViewHolder> {
        private List<String> allData;
        
        //checkPosition 관리
        private int checkedPosition = 0;

        public DateAdapter(List<String> items) {
            this.allData = items;
        }

        public void setData(List<String> items) {
            this.allData = items;
            notifyDataSetChanged();
        }

        public void setPosition(int position) {
            this.checkedPosition = position;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_calendar, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            String textItem = allData.get(position);

            if (holder instanceof ItemViewHolder) {
                holder.tv_day_of_week.setText(textItem.split("/")[3]);
                holder.tv_day.setText(textItem.split("/")[2]);

                if (checkedPosition == position){
                    Log.d(TAG, "checkedPosition - : " + textItem);

                    holder.cl_parent_view.setBackground(mContext.getResources().getDrawable(R.drawable.circular_calendar_background));
                    holder.tv_day_of_week.setTextColor(mContext.getResources().getColor(R.color.color_ffffff));
                    holder.tv_day.setTextColor(mContext.getResources().getColor(R.color.color_f8f8f8));

                } else {
                    holder.cl_parent_view.setBackgroundColor(mContext.getResources().getColor(R.color.color_ffffff));
                    holder.tv_day_of_week.setTextColor(mContext.getResources().getColor(R.color.color_b9b9b9));
                    holder.tv_day.setTextColor(mContext.getResources().getColor(R.color.color_282828));
                }

                holder.cl_parent_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "test : " );
                        if (checkedPosition != position) {
                            checkedPosition = position;
                            //viewpager 아이템 위치 변경
                            vp_bottom_container.setCurrentItem(position);
                            //상단의 text 변경
                            tv_camera_date_toolbar.setText(textItem.split("/")[0] + " / "
                                    + textItem.split("/")[1] + " / " + textItem.split("/")[2]);

                            notifyDataSetChanged();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return (allData != null) ? allData.size() : 0;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            private ConstraintLayout cl_parent_view;
            private TextView tv_day_of_week;
            private TextView tv_day;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                cl_parent_view = (ConstraintLayout) itemView.findViewById(R.id.cl_parent_view);
                tv_day_of_week = (TextView) itemView.findViewById(R.id.tv_day_of_week);
                tv_day = (TextView) itemView.findViewById(R.id.tv_day);
            }
        }
    }

    //하단 viewPager
    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
        public ItemAdapter(){}

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_inside_sample, parent, false);
            vh = new ItemViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                final ItemViewHolder viewHolder = (ItemViewHolder) holder;

                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

                layoutManager.setInitialPrefetchItemCount(3);

                DetailAdapter test = new DetailAdapter();

                holder.rv_nutrient_container.setLayoutManager(layoutManager);
                holder.rv_nutrient_container.setAdapter(test);


            }
        }

        @Override
        public int getItemCount() {
            return 7;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            public RecyclerView rv_nutrient_container;

            public void setData() {

            }

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                rv_nutrient_container = itemView.findViewById(R.id.rv_nutrient_container);

            }

        }
    }

    //내부 recyclerview
    public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DetailItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_nutrient, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof DetailItemViewHolder) {
                final DetailItemViewHolder viewHolder = (DetailItemViewHolder) holder;

                switch (position) {
                    case 0 :
                        viewHolder.tv_set_time.setText(mContext.getResources().getString(R.string.tv_set_time_morning));
                        break;
                    case 1:
                        viewHolder.tv_set_time.setText(mContext.getResources().getString(R.string.tv_set_time_noon));
                        break;
                    case 2:
                        viewHolder.tv_set_time.setText(mContext.getResources().getString(R.string.tv_set_time_evening));
                        break;
                }


            }
        }

        //TODO 아침 점심 저녁..
        @Override
        public int getItemCount() {
            return 3;
        }

        //holder
        class DetailItemViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title_calorie;
            TextView tv_nutrient_item_first_detail;
            TextView tv_nutrient_item_second_detail;
            TextView tv_nutrient_item_third_detail;

            RelativeLayout rl_bottom_container;
            ImageView iv_no_diet_data;
            TextView tv_no_diet_data;
            TextView tv_set_time;
            ImageView iv_plus_button;

            public DetailItemViewHolder(@NonNull View itemView) {
                super(itemView);

                tv_title_calorie = (TextView) itemView.findViewById(R.id.tv_title_calorie);
                tv_nutrient_item_first_detail = (TextView) itemView.findViewById(R.id.tv_nutrient_item_first_detail);
                tv_nutrient_item_second_detail = (TextView) itemView.findViewById(R.id.tv_nutrient_item_second_detail);
                tv_nutrient_item_third_detail = (TextView) itemView.findViewById(R.id.tv_nutrient_item_third_detail);

                rl_bottom_container = (RelativeLayout) itemView.findViewById(R.id.rl_bottom_container);
                iv_no_diet_data = (ImageView) itemView.findViewById(R.id.iv_no_diet_data);
                tv_no_diet_data = (TextView) itemView.findViewById(R.id.tv_no_diet_data);
                tv_set_time = (TextView) itemView.findViewById(R.id.tv_set_time);
                iv_plus_button = (ImageView) itemView.findViewById(R.id.iv_plus_button);
            }

        }
    }

}
