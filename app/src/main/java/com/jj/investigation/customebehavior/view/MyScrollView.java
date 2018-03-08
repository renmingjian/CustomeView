package com.jj.investigation.customebehavior.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by ${R.js} on 2018/3/7.
 */

public class MyScrollView extends ScrollView {

    private OnScrollInstanceListener onScrollInstanceListener;


    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollInstanceListener != null)
            onScrollInstanceListener.scrollInstance(t);
    }

    public void setOnScrollInstanceListener(OnScrollInstanceListener onScrollInstanceListener) {
        this.onScrollInstanceListener = onScrollInstanceListener;
    }

    /**
     * 滑动的距离监听
     */
    public interface OnScrollInstanceListener {
        void scrollInstance(int instance);
    }
}
