package com.jj.investigation.customebehavior.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.bean.HomeBean;
import com.jj.investigation.customebehavior.rv.MultiItemAdapter;
import com.jj.investigation.customebehavior.rv.MultiItemType;
import com.jj.investigation.customebehavior.rv.ViewHolder;

import java.util.List;

/**
 * 多条目布局例子
 * Created by ${R.js} on 2018/3/27.
 */

public class MultiItemAdapterSample<T> extends MultiItemAdapter<HomeBean.HomeSubBean> {

    public MultiItemAdapterSample(Context mContext, List<HomeBean.HomeSubBean> mData, MultiItemType<HomeBean.HomeSubBean> type) {
        super(mContext, mData, type);
    }

    @Override
    protected void convert(ViewHolder holder, int position, final HomeBean.HomeSubBean itemData) {
        /**
         * 这里还是需要对每个type进行判断，否则的话，不管是走哪个类型的item，所有的item都会设置数据，
         * 例如，在设置item2的时候，仍然会对item1进行设置，这时
         * holder.setText(R.id.tv_type1, itemData.getDesc())获取的TextView就是null，如果进行setText则
         * 会报空指针异常
         */
        switch (itemData.getType()) {
            case 1:
                holder.setText(R.id.tv_type1, itemData.getDesc());
                holder.setViewClick(R.id.tv_type1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, itemData.getDesc(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 2:
                holder.setText(R.id.tv_type2, itemData.getDesc());
                holder.setViewClick(R.id.tv_type2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, itemData.getDesc(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case 3:
                final RecyclerView recyclerView = holder.getView(R.id.rv_type3);
                final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(new OneItemAdapterSample(mContext, R.layout.item_inner_item, itemData.getStringList()));
                break;
            case 4:
                holder.setText(R.id.tv_type4, itemData.getDesc());
                holder.setViewClick(R.id.tv_type4, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, itemData.getDesc(), Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
}
