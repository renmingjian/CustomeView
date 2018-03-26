package com.jj.investigation.customebehavior.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.jj.investigation.customebehavior.R;

/**
 * 垂直滑动的效果
 * 自定义属性：
 * mIsScaleMenuView的属性值设置
 * <attr name="top_meun_scrollable" format="boolean"/>
 * Created by ${R.js} on 2018/3/7.
 */

public class VerticalScrollView extends FrameLayout {

    private ViewDragHelper mDragHelper;
    // menu布局
    private View mMenuView;
    // 内容布局
    private View mMainView;
    // 内容布局的孩子
    private View mChildView;
    // 拖动的最大范围
    private int mDragRange = 400;
    // 本类自定义View是否要拦截事件（不是最终的，看onInterceptTouchEvent方法）
    private boolean mIntercept = false;
    // 滑动距离的监听
    private OnScrollListener mOnScrollListener;
    // 刷新的监听
    private OnRefreshListener mOnRefreshListener;
    /**
     * 是否放大上面menuView：如果为false，则向下滑动时，最多滑动menuView的高度，滑动的距离等于这个高度时，
     * 不能再向下滑动，如果为true，则滑动的距离等于这个高度时，可以再向下滑动，并且向下滑动时放大menuView。
     */
    private boolean mIsScaleMenuView = true;
    // 进度框--用View表示，之后就可以添加任意类型的自定义loading框
    private View mLoadingView;
    // 刷新的状态
    private RefreshState mRefreshState = RefreshState.COMPLETED;

    /**
     * 刷新的状态
     */
    public enum RefreshState {
        // 刷新完成
        COMPLETED,
        // 刷新中
        REFRESHING
    }


    public VerticalScrollView(Context context) {
        this(context, null);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(attrs);
        init();
    }

    private void obtainAttrs(AttributeSet attrs) {
        final TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.VerticalScrollView);
        mIsScaleMenuView = ta.getBoolean(R.styleable.VerticalScrollView_top_meun_scrollable, true);
        ta.recycle();
    }

    private void init() {

        mDragHelper = ViewDragHelper.create(this, callback);
    }

    /**
     * 布局inflate完毕后调用，此时获取到两个子View。
     * 这里判断了子View是否是可以滑动控件：ScrollView和ListView（没有判断GridView和RecyclerView），
     * 判断的目的是为了处理拦截事件，不处理拦截事件也是可以的，但是如果子View是滑动控件，则一些滑动的效果不是
     * 想要的，所以判断了子View是否是滑动控件，或者子view的子view是否是滑动控件
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        if (getChildCount() != 2)
//            throw new InflateException("上下滑动布局必须有且仅有两个直接的子View");
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
        if (mMenuView instanceof ViewGroup) {
            mLoadingView = mMenuView.findViewById(R.id.pb_refresh);
        }
        if (mMainView instanceof WebView) {
            mIntercept = true;
            return;
        }
        if (mMainView instanceof ListView) {
            lvOnScrollListener((ListView) mMainView);
            return;
        }

        if (mMainView instanceof ScrollView) {
            svOnScrollViewListener((MyScrollView) mMainView);
            return;
        }

        if (mMainView instanceof ViewGroup) {
            mChildView = ((ViewGroup) mMainView).getChildAt(0);
            if (mChildView instanceof ListView) {
                lvOnScrollListener((ListView) mChildView);
                return;
            }
            if (mChildView instanceof MyScrollView) {
                svOnScrollViewListener((MyScrollView) mChildView);
            }
        }
    }

    /**
     * ScrollView的滑动监听，如果滑动到顶部需要让intercept为true
     */
    private void svOnScrollViewListener(MyScrollView scrollView) {
        scrollView.setOnScrollInstanceListener(new MyScrollView.OnScrollInstanceListener() {
            @Override
            public void scrollInstance(int instance) {
                mIntercept = instance == 0;
            }
        });
    }

    /**
     * ListView的滑动监听
     * 如果滑动到顶部需要让intercept为true
     */
    private void lvOnScrollListener(ListView listView) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                mIntercept = firstVisibleItem == 0 && mChildView.getTop() == 0;
            }
        });
    }

    /**
     * 该方法肯定是在测量完成之后调用，所以可以在该方法中获取子View的宽度.
     * 因为该View的大小是不会发生改变的，所以该方法也就只走一次
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDragRange = mMenuView.getMeasuredHeight();
//        open();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mIntercept && mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * ViewDragHelper的回调
     */
    private final ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mMainView == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

        /**
         * 限制mainView在垂直方向上滑动的距离
         *
         * @return mainView滑动后将要变成的顶部坐标
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            // 计算mainView的顶部将要变成的坐标值：当前的顶部坐标+滑动的距离
            int newTop = top + dy;
            // 如果超出MDragRange的值后不需要放大上部menuView，则需要限制
            if (!mIsScaleMenuView) {
                if (newTop > mDragRange) newTop = mDragRange;
            } else {
                // 如果需要放大，则不让上下两个View变化很大，尤其是上面部分
                if (newTop > mDragRange) newTop = top - dy / 2;
            }

            if (newTop < 0) newTop = 0;
            return newTop;
        }

        /**
         * 控制mainView的滑动，同时控制menuView的伴随滑动
         * mainView的滑动范围是：[0 - MDragRange]
         * menuView的滑动范围是：[-MDragRange / 2 - 0]
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            // 如果滑动的范围是在0-MDragRange内，则mainView滑动多少就给top值设置为多少，同时让menuView滑动
            // 的距离设置为mainView的一半。
            if (top <= mDragRange) {
                mMainView.layout(0, top, mMainView.getMeasuredWidth(), mMainView.getBottom());
                mMenuView.layout(0, -(mDragRange - top) / 2, mMenuView.getWidth(), mMenuView.getBottom());
                final float alpha = (float) top / mDragRange;
                mMenuView.setAlpha(alpha);
                if (mOnScrollListener != null)
                    mOnScrollListener.onScroll(top, mDragRange, 1 - alpha);
            } else {
                // 如果超出了mDragRange的范围，则判断是否需要让menuView进行放大
                if (!mIsScaleMenuView) return;
                final float scale = (float) top / mMenuView.getHeight();
                mMenuView.setScaleX(scale * scale);
                mMenuView.setScaleY(scale * scale);
                mMainView.layout(0, top, mMainView.getMeasuredWidth(), mMainView.getBottom());
                // 设置刷新的进度框是否显示
                if (!mIsScaleMenuView) return;
                if (mLoadingView == null) return;
                if (mRefreshState == RefreshState.REFRESHING) {
                    mLoadingView.setVisibility(View.VISIBLE);
                } else if (mLoadingView != null) {
                    if (top > (mDragRange + 100) && mLoadingView.getVisibility() == View.INVISIBLE) {
                        mLoadingView.setVisibility(View.VISIBLE);
                    } else if (top <= (mDragRange + 100) && mLoadingView.getVisibility() == View.VISIBLE) {
                        mLoadingView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }

        /**
         * 当手指抬起时执行这个方法
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            whenFingerUp();
            shouldRefresh();
        }
    };

    /**
     * 是否需要刷新
     */
    private void shouldRefresh() {
        // 如果不设置放大效果，则刷新数据也不给，因为不放大，getTop永远小于mDragRange + 100
        if (!mIsScaleMenuView) return;
        if (mMainView.getTop() > (mDragRange + 100)) {
            mRefreshState = RefreshState.REFRESHING;
            if (mOnRefreshListener != null) {
                mOnRefreshListener.onRefresh();
            }
        } else {
            mRefreshState = RefreshState.COMPLETED;
        }
    }

    /**
     * 当手抬起时，判断是要close，还是要open
     */
    private void whenFingerUp() {
        if (mMainView.getTop() < mDragRange / 2) {
            close();
        } else {
            open();
        }
    }

    /**
     * 完全遮盖上面menu部分:
     * 调用了smoothSlideViewTo方法之后，onViewPositionChanged方法也会走，在该方法中同时对两个View进行滑动，
     * 所以只需滑动mMainView即可，没有必要再调用mDragHelper.smoothSlideViewTo(mMenuView, 0, -mDragRange);
     */
    public void close() {
        mDragHelper.smoothSlideViewTo(mMainView, 0, 0);
        // mDragHelper.smoothSlideViewTo(mMenuView, 0, -mDragRange);
        ViewCompat.postInvalidateOnAnimation(VerticalScrollView.this);
    }

    /**
     * 完全打开上面menu部分
     */
    public void open() {
        mDragHelper.smoothSlideViewTo(mMainView, 0, mDragRange);
        ViewCompat.postInvalidateOnAnimation(VerticalScrollView.this);
    }

    /**
     * 设置刷新的状态
     *
     * @param refreshState 刷新的状态
     */
    public void setRefreshState(RefreshState refreshState) {
        this.mRefreshState = refreshState;
        if (mLoadingView != null)
            mLoadingView.setVisibility(this.mRefreshState == RefreshState.COMPLETED ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * 设置是否放大menuView
     *
     * @param isScaleMenuView true:放大；false：不放大
     */
    public void setIsScaleMenuView(boolean isScaleMenuView) {
        this.mIsScaleMenuView = isScaleMenuView;
    }

    /**
     * 动态设置滑动的范围
     *
     * @param dragRange 默认为顶部menuView的高度
     */
    public void setDragRange(int dragRange) {
        this.mDragRange = dragRange;
    }

    /**
     * invalide是为了引起onDraw回调，onDraw又调用computeScroll；
     * 所以调用invalidate()是为了调用computeScroll()
     */
    @Override
    public void computeScroll() {
        // 若滚动动画没有结束，即位置发生改变，则刷新新的位置
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(VerticalScrollView.this);
        }
    }

    /**
     * Main布局垂直滑动距离的监听
     */
    public interface OnScrollListener {
        /**
         * @param instance    滑动的距离
         * @param maxInstance 最大的滑动距离
         * @param alpha       滑动的距离与上面menu布局高度的比值
         */
        void onScroll(int instance, int maxInstance, float alpha);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    /**
     * 刷新的监听
     */
    public interface OnRefreshListener {
        void onRefresh();
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }
}
