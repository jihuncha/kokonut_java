package com.huni.engineer.kokonutjava.ui.popup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.huni.engineer.kokonutjava.R;
import com.huni.engineer.kokonutjava.proto.response.JSresponseAnalyze;

import java.util.List;

public class BottomDialog extends BottomSheetDialogFragment {
    public static final String TAG = BottomDialog.class.getSimpleName();

    private Context mContext;
//    private OnClickListener mListener;
    private OnCloseModal mCloseListener;

    private ImageView iv_close_modal;
    private RecyclerView rv_photo_list;
    private List<JSresponseAnalyze> all_list;

    //check 를 위해 public 설정
//    public ProfileListAdapter adapter;

    public interface OnCloseModal {
        void onCloseModal();
    }

//    public interface OnClickListener {
//        void onChangeProfile(JSScsProfileAllList changeProfile);
//    }

    public BottomDialog(){

    }

    public BottomDialog(Context mContext, List<JSresponseAnalyze> items) {
        this.mContext = mContext;
        this.all_list = items;
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

//        iv_close_modal = v.findViewById(R.id.iv_close_modal);
//        iv_close_modal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCloseListener.onCloseModal();
//            }
//        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_photo_list = v.findViewById(R.id.rv_photo_list);
        rv_photo_list.setLayoutManager(manager); // LayoutManager 등록
//        adapter = new ProfileListAdapter(profileList);
//        rv_list.setAdapter(adapter);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

//    public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ProfileListHolder> {
//        private List<JSScsProfileAllList> data;
//
//        public class ProfileListHolder extends RecyclerView.ViewHolder {
//            public RelativeLayout rl_data_area;
//            public CircleImageView iv_profile_image;
//            public LinearLayout ll_right_data_area;
//            public TextView tv_name;
//            public TextView tv_member_one_line;
//            public ImageView iv_selected_profile;
//
//            public ProfileListHolder(View itemView) {
//                super(itemView);
//                rl_data_area = (RelativeLayout) itemView.findViewById(R.id.rl_data_area);
//                iv_profile_image = (CircleImageView) itemView.findViewById(R.id.iv_profile_image);
//                ll_right_data_area = (LinearLayout) itemView.findViewById(R.id.ll_right_data_area);
//                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
//                tv_member_one_line = (TextView) itemView.findViewById(R.id.tv_member_one_line);
//                iv_selected_profile = (ImageView) itemView.findViewById(R.id.iv_selected_profile);
//            }
//        }
//
//        public ProfileListAdapter(List<JSScsProfileAllList> data) {
//            this.data = data;
//        }
//
//        public void setData(List<JSScsProfileAllList> data) {
//            this.data = data;
//        }
//
//        @Override
//        public ProfileListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_sub_profile, parent, false);
//            return new ProfileListHolder(itemView);
//        }
//
//
//        @Override
//        public void onBindViewHolder(@NonNull ProfileListHolder holder, int position) {
//            JSScsProfileAllList item = data.get(position);
//
//            if (holder instanceof ProfileListHolder) {
//                holder.tv_name.setText(item.getNickName());
//                if (TextUtils.isEmpty(item.getStatusMessage())) {
//                    holder.tv_member_one_line.setVisibility(View.GONE);
//                } else {
//                    holder.tv_member_one_line.setText(item.getStatusMessage());
//                    holder.tv_member_one_line.setVisibility(View.VISIBLE);
//                }
//
//
//                if (item.isCheckItem()) {
//                    holder.iv_selected_profile.setVisibility(View.VISIBLE);
//                } else {
//                    holder.iv_selected_profile.setVisibility(View.GONE);
//                }
//
//                //이미지
//                if (item.getThumbUrl() != null && !TextUtils.isEmpty(item.getThumbUrl())) {
//                    GlideUtil.loadAlreadyCropImage(mContext, holder.iv_profile_image, item.getThumbUrl(), "ProfileListHolder");
//                } else {
//                    Log.d(TAG, "defaultNum : " + item.getDefaultImg());
//                    BitmapDrawable drawable = Utils.getDrawableThumb(mContext, item.getDefaultImg());
//                    holder.iv_profile_image.setImageBitmap(drawable.getBitmap());
//                }
//
//                holder.rl_data_area.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mListener.onChangeProfile(item);
//                    }
//                });
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return data != null ? data.size() : 0;
//        }
//    }
}