package com.jj.investigation.customebehavior.rv;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 给RecyclerView添加header或者footer，该wrapper整体就是一个Adapter
 * Created by ${R.js} on 2018/3/26.
 */

public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    /* 头部View type类型，如果头部只有一种item，则使用该类型，如果还有其他类型，则在该值的基础上+1 */
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    /* 尾部View type类型 */
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    /* 头部View集合 */
    private SparseArrayCompat<View> mHeaders = new SparseArrayCompat<>();
    /* 尾部View集合 */
    private SparseArrayCompat<View> mFooters = new SparseArrayCompat<>();
    /* 除了头尾部之外的adapter--也就是给那个需要添加头尾部的adapter */
    private RecyclerView.Adapter mInnerAdapter;
    /* 包括头尾部的整体adapter */
    private RecyclerView.Adapter mWholeAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter innerAdapter, Context context) {
        this.mInnerAdapter = innerAdapter;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据type类型去集合中找是不是有和该type类型对应的布局View，如果有，则加载头部或者底部的ViewHolder
        // 不是头尾部的交给MInnerAdapter自己去判断返回
        if (mHeaders.get(viewType) != null)
            return ViewHolder.getViewHolder(parent.getContext(), mHeaders.get(viewType));
        if (mFooters.get(viewType) != null)
            return ViewHolder.getViewHolder(parent.getContext(), mFooters.get(viewType));
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 如果是头部或者尾部的item，则不需要调用该方法，调用onBindViewHolder方法的作用是初始化数据，而头部
        // 和尾部的数据初始化时在addHeaderView之前做的，也就是在onBindViewHolder之前已经做完
        if (isHeaderPosition(position) || isFooterPosition(position)) return;
        mInnerAdapter.onBindViewHolder(holder, position - getHeaderCount());
    }

    @Override
    public int getItemViewType(int position) {
        /**
         * 返回各个item的类型，需要判断所有item，如果当前位置是添加的头部item，则返回头部item的类型，如果当
         * 前位置是底部item，则返回底部item的类型。如果不是头部和尾部，则返回类型让minnerAdapter去判断.
         * 这里position的值需要准确设置
         */
        if (isHeaderPosition(position))
            return mHeaders.keyAt(position);
        if (isFooterPosition(position))
            return mFooters.keyAt(position - mHeaders.size() - mInnerAdapter.getItemCount());
        return mInnerAdapter.getItemViewType(position - getHeaderCount());
    }

    /**
     * 获取所有item的数量：包括了头部和底部item
     */
    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + mHeaders.size() + mFooters.size();
    }

    /**
     * 判断该位置是否是头部item
     *
     * @param position item的位置
     */
    private boolean isHeaderPosition(int position) {
        return position < mHeaders.size();
    }

    /**
     * 判断该位置是否是底部item
     *
     * @param position item的位置
     */
    private boolean isFooterPosition(int position) {
        return position >= (mInnerAdapter.getItemCount() + mHeaders.size());
    }

    /**
     * 获取除了头部和底部item之外的所有item的数量
     */
    public int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    /**
     * 添加一个头部item
     * @param view 头部View
     */
    public void addHeaderView(View view) {
        mHeaders.put(BASE_ITEM_TYPE_HEADER + mHeaders.size(), view);
    }

    /**
     * 添加一个底部item
     */
    public void addFooterView(View view) {
        mFooters.put(BASE_ITEM_TYPE_FOOTER + mFooters.size(), view);
    }

    /**
     * 获取添加的头部item个数
     */
    public int getHeaderCount() {
        return mHeaders.size();
    }

    /**
     * 获取添加的底部item个数
     */
    public int getFooterCount() {
        return mFooters.size();
    }
}
