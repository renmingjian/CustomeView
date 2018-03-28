package com.jj.investigation.customebehavior.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用Adapter
 * Created by ${R.js} on 2018/3/26.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    private int mLayoutId;
    protected List<T> mData;
    protected OnItemClickListener<T> mItemClickListener;


    public CommonAdapter(Context mContext, int mLayoutId, List<T> mData) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mData = mData;
        if (this.mData == null)
            this.mData = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, parent, mLayoutId);
        final int position = viewHolder.getAdapterPosition();
        final View view = viewHolder.getConvertView();
        view.setClickable(true);
        System.out.println("点击一下呗");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("onClick");
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position, mData.get(position));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        convert(holder, pos, mData.get(position));
    }

    /**
     * Holder和View的数据绑定
     */
    protected abstract void convert(ViewHolder holder, int position, T itemData);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 添加一条数据并刷新
     *
     * @param t 具体数据
     */
    public void addData(T t) {
        if (t == null) return;
        mData.add(t);
        notifyDataSetChanged();
        notifyItemInserted(mData.indexOf(t));
    }

    /**
     * 添加一条数据并刷新
     *
     * @param position 数据添加的位置
     * @param t        具体数据
     */
    public void addData(int position, T t) {
        if (t == null) return;
        mData.add(position, t);
        notifyItemInserted(position);
    }

    /**
     * 删除一条数据
     *
     * @param t 具体数据
     */
    public void removeData(T t) {
        if (t == null || !mData.contains(t)) return;
        mData.remove(t);
        notifyItemRemoved(mData.indexOf(t));
    }

    /**
     * 删除一条数据
     *
     * @param position 数据的位置
     */
    public void removeData(int position) {
        mData.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    /**
     * 替换一条数据
     *
     * @param position 位置
     * @param t        具体数据
     */
    public void replaceItemData(int position, T t) {
        mData.remove(position);
        mData.add(position, t);
        notifyItemChanged(position, t);
    }

    /**
     * 替换所有的数据
     *
     * @param data 所有的数据
     */
    public void replaceAll(List<T> data) {
        if (data == null) return;
        mData.clear();
        mData = data;
        notifyDataSetChanged();
    }

    /**
     * 条目点击事件监听
     */
    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
