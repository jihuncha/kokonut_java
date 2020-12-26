package com.huni.engineer.kokonutjava.ui.popup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.huni.engineer.kokonutjava.KokonutSettings;
import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.common.DatabaseManager;
import com.huni.engineer.kokonutjava.common.data.DailyFoodInfo;
import com.huni.engineer.kokonutjava.proto.JSFoodInfo;
import com.huni.engineer.kokonutjava.proto.response.JSresponseAnalyze;
import com.huni.engineer.kokonutjava.ui.main.MainTabActivity;
import com.huni.engineer.kokonutjava.utils.DateUtils;

import java.util.List;

public class BottomDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final String TAG = BottomDialog.class.getSimpleName();

    private Context mContext;
//    private OnClickListener mListener;
    private OnCloseModal mCloseListener;

    private ImageView iv_close_modal;
    private JSresponseAnalyze myItem;

    private TextView tv_title;
    private TextView tv_consume_title;
    private TextView tv_consume_detail;

    private RecyclerView rv_time_info;
    private RecyclerView rv_photo_list;

    private Button bt_save_data;

    //check 를 위해 public 설정
    public FoodListAdapter mAdapter;

    public interface OnCloseModal {
        void onCloseModal();
    }

//    public interface OnClickListener {
//        void onChangeProfile(JSScsProfileAllList changeProfile);
//    }

    public BottomDialog(){ }

    public BottomDialog(Context mContext, JSresponseAnalyze myItem) {
        this.mContext = mContext;
        this.myItem = myItem;
    }

//    public void setListener(OnClickListener listener) {
//        mListener = listener;
//    }

    public void setListenerClose(OnCloseModal listener) {
        mCloseListener = listener;
    }

    //style 적용 -> round
    //https://stackoverflow.com/questions/48130347/how-to-set-theme-in-fragment-extends-bottomsheetdialogfragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,  R.style.AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_food_list, container, false);

        LinearLayoutManager manager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        tv_title = v.findViewById(R.id.tv_title);
        tv_title.setText(myItem.getName());

        tv_consume_title = v.findViewById(R.id.tv_consume_title);
        tv_consume_detail = v.findViewById(R.id.tv_consume_detail);
        tv_consume_detail.setText(myItem.getCalories() + "Kcal");

        rv_time_info = v.findViewById(R.id.rv_time_info);
        rv_photo_list = v.findViewById(R.id.rv_photo_list);

        rv_time_info.setLayoutManager(manager); // LayoutManager 등록
        mAdapter = new FoodListAdapter();
        rv_time_info.setAdapter(mAdapter);

        bt_save_data = v.findViewById(R.id.bt_save_data);
        bt_save_data.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListHolder> {
        public FoodListAdapter(){}

        public class FoodListHolder extends RecyclerView.ViewHolder {
            private TextView tv_result_detail;

            public FoodListHolder(@NonNull View itemView) {
                super(itemView);

                tv_result_detail = (TextView) itemView.findViewById(R.id.tv_result_detail);
            }

        }

        @NonNull
        @Override
        public FoodListAdapter.FoodListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_bottom_dialog, parent, false);
            return new FoodListHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodListAdapter.FoodListHolder holder, int position) {
            if (holder instanceof FoodListHolder) {
                FoodListHolder viewHolder = (FoodListHolder) holder;

                switch (position) {
                    case 0:
                        viewHolder.tv_result_detail.setText(KokonutSettings.getInstance(mContext).getTodayCal());
                        break;
                    case 1:
                        viewHolder.tv_result_detail.setText(DateUtils.toTimeString());
                        break;

                    case 2:
                        switch (KokonutSettings.getInstance(mContext).getCurrentClickPos()) {
                            case 0:
                                viewHolder.tv_result_detail.setText(mContext.getResources().getString(R.string.tv_set_time_morning));
                                break;
                            case 1:
                                viewHolder.tv_result_detail.setText(mContext.getResources().getString(R.string.tv_set_time_noon));
                                break;
                            case 2:
                                viewHolder.tv_result_detail.setText(mContext.getResources().getString(R.string.tv_set_time_evening));
                                break;
                        }
                        break;
                }
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_save_data:
                Log.d(TAG,"onClick - bt_save_data");

                String changeDate = KokonutSettings.getInstance(mContext).getTodayCal();

                String[] array_all = changeDate.split("/");

                //TODO 예외처리해야함
                if (array_all != null && array_all.length > 2) {
                    DailyFoodInfo myInfo = new DailyFoodInfo();
                    myInfo.set(myItem, array_all[0] + "-" + array_all[1] + "-" + array_all[2],
                            KokonutSettings.getInstance(mContext).getCurrentClickPos());

                    Log.d(TAG, "check - " + myInfo.toString());

                    DatabaseManager.getInstance(mContext).insertFoodInfo(myInfo);
                }

                Handler mHandler = new Handler();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(mContext, MainTabActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }, 1000);

                break;
        }
    }
}