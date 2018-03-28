package com.jj.investigation.customebehavior.rv;

/**
 * Created by ${R.js} on 2018/3/26.
 */

public interface MultiItemType<T> {

    int getLayoutId(int viewType);

    int getItemViewType(int position, T item);

}
