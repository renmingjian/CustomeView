package com.jj.investigation.customebehavior.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.rv.HeaderAndFooterWrapper;

/**
 *
 * Created by ${R.js} on 2018/3/28.
 */

public class HeaderViewWrapper extends HeaderAndFooterWrapper {

    private View headerView;
    private TextView tv_header1;

    public HeaderViewWrapper(RecyclerView.Adapter mInnerAdapter, Context context) {
        super(mInnerAdapter, context);
        initLayout(context);
    }

    private void initLayout(Context context) {
        headerView = View.inflate(context, R.layout.header_view, null);
        initView();
    }

    private void initView() {
        tv_header1 = (TextView) headerView.findViewById(R.id.tv_header1);
    }

    public void setData(String string) {
        tv_header1.setText(string);
    }

    public View getHeaderView() {
        return headerView;
    }
}
