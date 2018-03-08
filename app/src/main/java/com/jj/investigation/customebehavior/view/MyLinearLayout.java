package com.jj.investigation.customebehavior.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 该View配合SlideMenu使用，并且是在slide_main布局中作为跟布局使用，主要作用是：
 * 如果slideMenu是打开状态，则main布局的跟布局不拦截所有时间，交给子View获取，
 * 如果是关闭状态，则此时主要显示的是menu布局，所以main布局的所有字view不应该响应
 * 所有事件，所以此时要返回true，即让跟布局拦截掉所有事件。
 * Created by ${R.js} on 2018/3/8.
 */

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return SlideMenu.mOpenStatus || super.onInterceptTouchEvent(ev);
    }


}
