package com.jj.investigation.customebehavior.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 侧滑菜单
 * 由于通常写ViewGroup的时候对子view的测量和摆放，所以此时我们可以直接继承自系统已有的ViewGroup。
 * Created by ${R.js} on 2018/3/6.
 */

public class SlideMenu extends FrameLayout {

    private ViewDragHelper mDragHelper;
    // menu布局
    private View mMenuView;
    // 内容布局
    private View mMainView;
    // menu的宽度
    private int mMenuWidth;
    // 主内容的宽度
    private int mMainWidth;
    // 拖动的最大范围
    private int mDragRange;

    public SlideMenu(Context context) {
        this(context, null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, callback);
    }


    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        /**
         * 判断是否捕获当前所触摸的子View的触摸事件
         * @param child 当前所触摸的子View
         * @return true：捕获
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mMenuView || child == mMainView;
        }

        /**
         * 当一个View被捕获的时候回调
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 100;
        }

        /**
         * 控制View在水平方向的滑动
         * @param child 当前触摸的子View
         * @param left 表示ViewDragHelper认为child的left将要变成的值，left=child.getLeft()+dx
         * @param dx 表示本次手指水平移动的距离
         * @return 返回的值表示我们最终认为child的left将要变成的值
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mMainView) {
                if (left < 0) left = 0;
                if (left > mDragRange) left = mDragRange;
            }
//            if (child == mMenuView) {
//                if (left < -mMenuWidth) left = -mMenuWidth;
//                if (left > -mDragRange) left = -mDragRange;
//            }
            System.out.println("leftt = " + left + ",,," + (-mMenuWidth + mDragRange));
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        /**
         * 当View的位置发生改变的时候调用
         * @param changedView 位置改变的View
         * @param left changedView的最新的left
         * @param top changedView的最新的top
         * @param dx 表示changedView水平移动的距离
         * @param dy 表示changedView垂直移动的距离
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == mMenuView) {
                int newLeft = left + dx;
                if (newLeft > mDragRange) newLeft = mDragRange;
                if (newLeft < 0) newLeft = 0;
                mMainView.layout(newLeft, 0, mMainWidth, mMainView.getBottom());
            }
            if (changedView == mMainView) {
                System.out.println("getLeft = " + mMenuView.getLeft());
//                int newLeft = mMenuView.getLeft() + dx;
//                if (newLeft < -mMenuWidth) newLeft = -mMenuWidth;
//                if (newLeft > -mDragRange) newLeft = -mDragRange;
//                mMenuView.layout(newLeft, 0, mMenuWidth, mMenuView.getBottom());
//                System.out.println("newLeft = " + newLeft);
//                System.out.println("left = " + left + ", dx = " + dx);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    };

    /**
     * 当布局填充完毕之后调用，在该方法中可以获取子View
     * 我们这里只能有2个子孩子
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2)
            throw new InflateException("侧滑布局必须有且仅有两个直接的子View");
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    /**
     * 该方法肯定是在测量完成之后调用，所以可以在该方法中获取子View的宽度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mMenuWidth = mMenuView.getMeasuredWidth();
        mMainWidth = mMainView.getMeasuredWidth();
        mDragRange = (int) (getMeasuredWidth() * 0.6f);

        System.out.println("mMenuWidth = " + mMenuWidth);
        System.out.println("mMainWidth = " + mMainWidth);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }
}
