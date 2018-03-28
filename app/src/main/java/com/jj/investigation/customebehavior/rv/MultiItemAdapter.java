package com.jj.investigation.customebehavior.rv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 多类型item RecyclerView
 * Created by ${R.js} on 2018/3/26.
 */

public abstract class MultiItemAdapter<T> extends CommonAdapter<T> {

    protected MultiItemType<T> mMultiItemType;

    public MultiItemAdapter(Context mContext, List<T> mData, MultiItemType<T> type) {
        super(mContext, -1, mData);
        mMultiItemType = type;
    }

    /**
     * 多个条目的需要重写CommonAdapter的onCreateViewHolder方法，因为多类型条目，
     * 每个都需要自己的ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final int layoutId = mMultiItemType.getLayoutId(viewType);
        final ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, parent, layoutId);
        // 设置条目点击事件
        final View view = viewHolder.getConvertView();
        view.setClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    final int position = viewHolder.getAdapterPosition();
                    mItemClickListener.onItemClick(position, mData.get(position));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, position, mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemType.getItemViewType(position, mData.get(position));
    }
}
