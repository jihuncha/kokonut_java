package com.huni.engineer.kokonutjava.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.huni.engineer.kokonutjava.KokonutSettings;
import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.common.DatabaseManager;
import com.huni.engineer.kokonutjava.common.PermissionHandler;
import com.huni.engineer.kokonutjava.common.data.DailyFoodInfo;
import com.huni.engineer.kokonutjava.ui.main.camera.CameraCaptureActivity;
import com.huni.engineer.kokonutjava.utils.AppBarStateChangeListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CameraFragment extends BaseTabFragment implements View.OnClickListener{
    public final String TAG = CameraFragment.class.getSimpleName();

    public CameraFragment(Activity activity, LayoutInflater inflater) {
        super(activity, inflater, R.layout.fragment_camera);

        mViewModel = ViewModelProviders.of((FragmentActivity) mActivity).get(CameraFragmentViewModel.class);

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

    private TextView tv_camera_title_toolbar;
    private TextView tv_camera_date_toolbar;

    private RecyclerView rv_date_selector;

    private PieChart mPieChart;

    //뷰페이저 어댑터
    private ViewPager2 vp_bottom_container;
    private DateAdapter dateAdapter;

    private ItemAdapter itemAdapter;

    private PermissionHandler mCameraPermission;

    //내 전체 데이터 (음식에 관한)
    private List<DailyFoodInfo> myDataAll;

    private CameraFragmentViewModel mViewModel;
    private DatabaseManager mDbManager;

    private void initView(View root) {
        Log.d(TAG, "initView");

        initObserve();

        mDbManager = DatabaseManager.getInstance(mContext);

        checkAndGetData();

        mCameraPermission   = new PermissionHandler(mActivity, PermissionHandler.REQ_CAMERA, false);

        myDataAll = new ArrayList<>();
        myDataAll = DatabaseManager.getInstance(mContext).getMyFoodInfoAll();

        cl_parent_view = (CoordinatorLayout) root.findViewById(R.id.cl_parent_view);

        appbar = (AppBarLayout) root.findViewById(R.id.appbar);

        toolbar = (Toolbar) root.findViewById(R.id.toolbar);

        ll_toolbar_area = (LinearLayout) root.findViewById(R.id.ll_toolbar_area);
        ll_toolbar_area.setOnClickListener(this);

        tv_camera_title = (TextView) root.findViewById(R.id.tv_camera_title);
        tv_camera_title.setOnClickListener(this);
        tv_camera_date_title = (TextView) root.findViewById(R.id.tv_camera_date_title);
        tv_camera_date_title.setOnClickListener(this);

        tv_camera_title_toolbar = (TextView) root.findViewById(R.id.tv_camera_title_toolbar);
        tv_camera_date_toolbar = (TextView) root.findViewById(R.id.tv_camera_date_toolbar);

        rv_date_selector = (RecyclerView) root.findViewById(R.id.rv_date_selector);

        mPieChart = (PieChart) root.findViewById(R.id.piechart);

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

        initPager();
    }

    private void initObserve() {
        Log.d(TAG, "initObserve");

        mViewModel.myCurrentData.observe((LifecycleOwner) mActivity, new Observer<List<DailyFoodInfo>>() {
            @Override
            public void onChanged(List<DailyFoodInfo> dailyFoodInfos) {
                Log.d(TAG, "test 222 : " + dailyFoodInfos);

                //TODO 결국 날짜별로 정렬해줘야하나??
                mViewModel.makeHash(dailyFoodInfos);

//                if (itemAdapter == null) {
//                    itemAdapter = new ItemAdapter(dailyFoodInfos);
//                    vp_bottom_container.setAdapter(itemAdapter);
//                    vp_bottom_container.registerOnPageChangeCallback(mPageChange);
//                } else {
//                    itemAdapter.setData(dailyFoodInfos);
//                    itemAdapter.notifyDataSetChanged();
//                    if (vp_bottom_container != null) {
//                        vp_bottom_container.unregisterOnPageChangeCallback(mPageChange);
//                    }
//                    vp_bottom_container.registerOnPageChangeCallback(mPageChange);
//                }
            }
        });

    }

    //초기진입시 데이터를 가져온다.
    private void checkAndGetData() {
        Log.d(TAG, "checkAndGetData");

        DecimalFormat df = new DecimalFormat("00");
        Calendar currentCalendar = Calendar.getInstance();

        String today = null;

        today = currentCalendar.get(Calendar.YEAR) + "-" +
                df.format(currentCalendar.get(Calendar.MONTH) + 1) + "-" +
                df.format(currentCalendar.get(Calendar.DATE));

        Log.d(TAG, "today : " + today);

        //일주일 뒤 계산
        String afterOneWeek = null;

        currentCalendar.add(currentCalendar.DATE, 6);
        afterOneWeek = currentCalendar.get(Calendar.YEAR) + "-" +
                df.format(currentCalendar.get(Calendar.MONTH) + 1) + "-" +
                df.format(currentCalendar.get(Calendar.DATE));

        Log.d(TAG, "afterOneWeek : " + afterOneWeek);

        //ViewModel 에 전달
        mViewModel.myCurrentData.postValue(mDbManager.getFoodDataForDate(today, afterOneWeek));
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

        Log.d(TAG, "check : " + calendarToday);

        //오늘 날짜 갖고있기.
        KokonutSettings.getInstance(mContext).setTodayCal(calendarToday);

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

    //하단 pager
    private void initPager() {
        if (itemAdapter == null) {
            itemAdapter = new ItemAdapter();
            vp_bottom_container.setAdapter(itemAdapter);
            vp_bottom_container.registerOnPageChangeCallback(mPageChange);
        } else {
            itemAdapter.notifyDataSetChanged();
            if (vp_bottom_container != null) {
                vp_bottom_container.unregisterOnPageChangeCallback(mPageChange);
            }
            vp_bottom_container.registerOnPageChangeCallback(mPageChange);
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

            KokonutSettings.getInstance(mContext).setTodayCal(calendarDateAll.get(position));

            Log.d(TAG, "real: " + KokonutSettings.getInstance(mContext).getTodayCal());

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

    //하단 viewPager -> viewPager
    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
        public List<DailyFoodInfo> myList;

        public ItemAdapter(){}

        public ItemAdapter(List<DailyFoodInfo> myList) {
            this.myList = myList;
        }

        public void setData(List<DailyFoodInfo> myList) {
            this.myList = myList;
        }

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

                DetailAdapter detailAdapter = new DetailAdapter();

                holder.rv_nutrient_container.setLayoutManager(layoutManager);
                holder.rv_nutrient_container.setAdapter(detailAdapter);
            }
        }

        //고정값
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

                viewHolder.iv_plus_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "iv_plus_button/onClick");

                        KokonutSettings.getInstance(mContext).setCurrentClickPos(position);

                        Log.d(TAG, "pos check - " + KokonutSettings.getInstance(mContext).getCurrentClickPos());

                        if (mCameraPermission.checkPermissions(true, "initiatePopupWindow/tv_profile_take_pic")) {
                            Intent intent = new Intent();
                            intent.setClass(mActivity, CameraCaptureActivity.class);
                            mActivity.startActivityForResult(intent, 1234);
                        }
                    }
                });


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

    /**
     *
     *  원 그래프 그리기.
     *
     * */
//    public void initPieChart(DailyFoodInfo pieChartData) {
//        Log.d(TAG, "initPieChart");
//
//        if (pieChartData == null) {
//            Log.e(TAG, "processPieData NULL!!");
//            return;
//        }
//
//        Log.d(TAG, "processPieData - " + pieChart);
//        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(5,10,5,5);
//
//        pieChart.setDragDecelerationFrictionCoef(0.95f);
//
//        pieChart.setDrawHoleEnabled(false);
//        pieChart.setHoleColor(Color.WHITE);
//        pieChart.setTransparentCircleRadius(61f);
//
//        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
//
//        yValues.add(new PieEntry(Integer.parseInt(pieChartData.getSellFood()),getResources().getString(R.string.main_deadline_pie_graph_food)));
//        yValues.add(new PieEntry(Integer.parseInt(pieChartData.getSellBeer()),getResources().getString(R.string.main_deadline_pie_graph_beer)));
//        yValues.add(new PieEntry(Integer.parseInt(pieChartData.getSellCock()),getResources().getString(R.string.main_deadline_pie_graph_cock)));
//
//        Description description = new Description();
//        description.setText(" "); //라벨
//        description.setTextSize(15);
//        pieChart.setDescription(description);
//        //하단 상세 설명(?) 안보이게 설정
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setNoDataText("test");
//
//        //하단 차트 정보 (막대 색갈별 정보) 안보이게 조정
//        Legend legend = pieChart.getLegend();
//        legend.setEnabled(false);
//
////        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션
//
//        PieDataSet dataSet = new PieDataSet(yValues," ");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//        int [] color={ getResources().getColor(R.color.color_fb7a63), getResources().getColor(R.color.color_fcc849),
//                getResources().getColor(R.color.color_6bd67c), getResources().getColor(R.color.color_c7abf6),
//                getResources().getColor(R.color.color_839afe), getResources().getColor(R.color.color_81c6fc),
//                getResources().getColor(R.color.color_4993cd)
//        };
//
//        dataSet.setColors(color);
//        pieChart.setDrawMarkers(false);
//        pieChart.setDrawEntryLabels(false);
//        pieChart.setTouchEnabled(false);
//
//        PieData data = new PieData((dataSet));
//        //value 값 숨기기.
//        data.setValueTextSize(10f);
//        data.setValueTextColor(Color.YELLOW);
//        data.setDrawValues(false);
//
//        pieChart.setData(data);
//    }


}
