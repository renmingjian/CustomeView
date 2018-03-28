package com.jj.investigation.customebehavior.adapter;

import android.content.Context;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.rv.CommonAdapter;
import com.jj.investigation.customebehavior.rv.ViewHolder;

import java.util.List;

/**
 * Created by ${R.js} on 2018/3/26.
 */

public class OneItemAdapterSample extends CommonAdapter<String> {

    public OneItemAdapterSample(Context mContext, int mLayoutId, List<String> mData) {
        super(mContext, mLayoutId, mData);
    }

    @Override
    protected void convert(ViewHolder holder, int position, final String string) {
        holder.setText(R.id.tv_item_inner, string);
//        holder.setViewClick(R.id.tv_item_inner, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, string, Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
