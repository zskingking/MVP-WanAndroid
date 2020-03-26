package com.example.baselibrary.widget;

import android.view.View;

import java.util.Calendar;

/**
 * des 防止快速点击抖动
 *
 * @author zs
 * @date 2020--3-13
 */
public class OnLimitClickHelper implements View.OnClickListener {
    public static final int LIMIT_TIME = 500;
    private long lastClickTime = 0;
    private OnLimitClickListener onLimitClickListener = null;

    public OnLimitClickHelper(OnLimitClickListener onLimitClickListener){
        this.onLimitClickListener = onLimitClickListener;
    }

    @Override
    public void onClick(View v) {
        long curTime = Calendar.getInstance().getTimeInMillis();
        if (curTime - lastClickTime > LIMIT_TIME) {
            lastClickTime = curTime;
            if(onLimitClickListener != null){
                onLimitClickListener.onClick(v);
            }
        }
    }
}