package com.huni.engineer.kokonutjava.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toolbar;

public class NonClickableToolbar extends Toolbar {
    public NonClickableToolbar(Context context) {
        super(context);
    }

    public NonClickableToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonClickableToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NonClickableToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
