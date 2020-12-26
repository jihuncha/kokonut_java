package com.huni.engineer.kokonutjava.ui.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huni.engineer.kokonutjava.R;

public class CommonPopup extends Dialog {
    public static final String TAG = CommonPopup.class.getSimpleName();

    private Context mContext;

    private String			mTitle;
    private String			mContent;
    private int             mType;
    private boolean mCancelable = false;


    private TextView ID_POPUP_MSG_TITLE;
    private TextView ID_POPUP_MSG_CONTENTS;

    private TextView ID_POPUP_MSG_BTN_1;
    private LinearLayout ID_POPUP_MSG_BTN_2;
    private TextView ID_POPUP_MSG_BTN_2_LEFT;
    private TextView ID_POPUP_MSG_BTN_2_RIGHT;

    private IPopupMsgListener     msgListener;

    public CommonPopup(@NonNull Context context) {
        super(context);
    }

    //type - 0 이면 버튼하나 / 1이면 버튼 2개
    public CommonPopup(Context context, String title, String content, int type, boolean cancelable) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.mTitle = title;
        this.mContent = content;
        this.mType = type;
        this.mCancelable = cancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE);

        Log.d(TAG, "onCreate/mCancelable : " + mCancelable);
        if (mCancelable) {
            //바깥 클릭시 캔슬이 가능한 popup
            setCanceledOnTouchOutside(true);
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //background 흐리게..
        lp.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lp.dimAmount= 0.6f;

        getWindow().setAttributes(lp);
        setContentView(R.layout.common_popup);

        setLayout();
    }

    private void setLayout(){
        ID_POPUP_MSG_TITLE = (TextView) findViewById(R.id.ID_POPUP_MSG_TITLE);
        ID_POPUP_MSG_CONTENTS = (TextView) findViewById(R.id.ID_POPUP_MSG_CONTENTS);

        ID_POPUP_MSG_BTN_1 = (TextView) findViewById(R.id.ID_POPUP_MSG_BTN_1);
        ID_POPUP_MSG_BTN_2 = (LinearLayout) findViewById(R.id.ID_POPUP_MSG_BTN_2);
        ID_POPUP_MSG_BTN_2_LEFT = (TextView) findViewById(R.id.ID_POPUP_MSG_BTN_2_LEFT);
        ID_POPUP_MSG_BTN_2_RIGHT = (TextView) findViewById(R.id.ID_POPUP_MSG_BTN_2_RIGHT);

        if (mType == 0) {
            ID_POPUP_MSG_BTN_1.setVisibility(View.VISIBLE);
            ID_POPUP_MSG_BTN_2.setVisibility(View.GONE);

            ID_POPUP_MSG_BTN_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msgListener != null) {
                        msgListener.onPositiveClick();
                    }
                    dismiss();
                }
            });
        } else {
            ID_POPUP_MSG_BTN_1.setVisibility(View.GONE);
            ID_POPUP_MSG_BTN_2.setVisibility(View.VISIBLE);

            ID_POPUP_MSG_BTN_2_LEFT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msgListener != null) {
                        msgListener.onNagativeClick();
                    }
                    dismiss();
                }
            });

            ID_POPUP_MSG_BTN_2_RIGHT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (msgListener != null) {
                        msgListener.onPositiveClick();
                    }
                    dismiss();
                }
            });
        }

        //Title
        if(mTitle == null) {
            ID_POPUP_MSG_TITLE.setVisibility(View.GONE);
        } else {
            ID_POPUP_MSG_TITLE.setVisibility(View.VISIBLE);
            ID_POPUP_MSG_TITLE.setText(mTitle);
        }

        //Content
        ID_POPUP_MSG_CONTENTS.setText(mContent);

    }

    public void setListener(IPopupMsgListener listener) {
        this.msgListener = listener;
    }

    @Override
    public void show() {
        try {
            super.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IPopupMsgListener {
        void onNagativeClick();
        void onPositiveClick();
    }
    public interface IPopupNaturalListener {
        void onNaturalClick();
    }

}
