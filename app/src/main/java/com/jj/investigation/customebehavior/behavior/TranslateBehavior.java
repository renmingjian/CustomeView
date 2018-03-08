package com.jj.investigation.customebehavior.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * 让FloatinActionButton在向上滑动的时候隐藏，在向下滑动的时候出现。
 *
 * Created by ${R.js} on 2018/3/5.
 */

public class TranslateBehavior extends FloatingActionButton.Behavior {

    /**
     * 是否是正在向上滑动，因为要执行动画，如果不添加限制条件，执行动画的操作会执行很多次，
     * 所以如果是正在滑动，就执行动画，否则不执行
     */
    private boolean isScrollUpward = false;

    public TranslateBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        // 如果是垂直滚动，则返回true，返回true之后，下面的方法才会执行，如果是横向滑动就返回false
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * @param coordinatorLayout
     * @param child             这里就是FloatingActionBar
     * @param target
     * @param dxConsumed
     * @param dyConsumed        向上和向下滑动的距离，向上为正，向下为负
     * @param dxUnconsumed
     * @param dyUnconsumed      该值一直为0
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 2) {
            // 向上滑动，隐藏fab
            if (!isScrollUpward) {
                final int transLateY = ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).bottomMargin
                        + child.getMeasuredHeight();
                child.animate().translationY(transLateY).setDuration(500).start();
                isScrollUpward = true;
            }
        } else if (dyConsumed < -2){
            // 向下滑动，显示fab
            if (isScrollUpward) {
                child.animate()
                        .translationY(0)
                        .setInterpolator(new OvershootInterpolator(1))
                        .setDuration(500)
                        .start();
                isScrollUpward = false;
            }
        }
    }
}
