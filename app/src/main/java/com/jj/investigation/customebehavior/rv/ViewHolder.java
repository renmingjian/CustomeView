package com.jj.investigation.customebehavior.rv;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView通用的ViewHolder，通过集合来存储每个View
 * Created by ${R.js} on 2018/3/26.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    /* 存储所有View的集合 */
    private SparseArrayCompat<View> mViews;
    /* 复用的条目 */
    private View mConvertView;
    private Context mContext;


    public ViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        mConvertView = itemView;
        mViews = new SparseArrayCompat<>();
//        setArrayData(mConvertView);
    }

    /**
     * 生成各个类型的条目所对应的viewHolder--头尾部条目使用该方法
     *
     * @param context  context
     * @param itemView 头尾部对应的布局View，与下面方法中的parent不同
     * @return viewHolder
     */
    public static ViewHolder getViewHolder(Context context, View itemView) {
        return new ViewHolder(context, itemView);
    }

    /**
     * 生成各个类型的条目所对应的viewHolder--普通条目使用该方法
     * 类型不同的item在生成ViewHolder时，唯一的依赖是layoutID
     *
     * @param context  context
     * @param parent   parent
     * @param layoutId 布局ID
     * @return viewHolder
     */
    public static ViewHolder getViewHolder(Context context, ViewGroup parent, int layoutId) {
        final View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(context, itemView);
    }

    /**
     * 先找到item下每个有ID的View，存进集合中
     * 如果传递来的View是ViewGroup，则递归该方法
     *
     * @param view
     */
    private void setArrayData(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup convertView = (ViewGroup) view;
            View childView;
            final int childSize = convertView.getChildCount();
            for (int i = 0; i < childSize; i++) {
                childView = convertView.getChildAt(i);
                if (childView.getId() != -1) {
                    mViews.put(childView.getId(), childView);
                }
                if (childView instanceof ViewGroup) {
                    setArrayData(childView);
                }
            }
        } else {
            if (view.getId() != -1) {
                mViews.put(view.getId(), view);
            }
        }
    }

    /**
     * 获取当前复用的item
     * 用来设置点击事件
     */
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过ID来获取每个View。
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 给TextView设置文字
     */
    public ViewHolder setText(int viewId, String text) {
        final TextView textView = getView(viewId);
        System.out.println("textView = " + textView);
        if (textView != null)
            textView.setText(text);
        return this;
    }

    /**
     * 给ImageView设置资源图片
     */
    public ViewHolder setImgResource(int viewId, int resId) {
        final ImageView imageView = getView(viewId);
        if (imageView != null)
            imageView.setImageResource(resId);
        return this;
    }

    /**
     * 设置控件是否可见
     */
    public ViewHolder setViewVisiable(int viewId, boolean visiable) {
        final View view = getView(viewId);
        if (view != null)
            view.setVisibility(visiable ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 给条目中的item设置点击事件
     */
    public ViewHolder setViewClick(int viewId, View.OnClickListener listener) {
        final View view = getView(viewId);
        if (view != null)
            view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnItemClickListener(View.OnClickListener listener) {
        mConvertView.setOnClickListener(listener);
        return this;
    }
}
