package com.jj.investigation.customebehavior.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.adapter.MultiItemAdapterSample;
import com.jj.investigation.customebehavior.bean.HomeBean;
import com.jj.investigation.customebehavior.rv.CommonAdapter;
import com.jj.investigation.customebehavior.rv.HeaderAndFooterWrapper;
import com.jj.investigation.customebehavior.rv.MultiItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 显示RecyclerView
 * Created by ${R.js} on 2018/3/21.
 */

public class RvMultiItemActivity extends BaseActivity {

    private RecyclerView rv_home;
    private List<HomeBean.HomeSubBean> mData;
    private MultiItemAdapterSample<HomeBean.HomeSubBean> adapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_rv;
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }

    @Override
    protected void initView() {
        rv_home = (RecyclerView) findViewById(R.id.rv_home);
    }

    @Override
    protected void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ReentrantLock().lock();
            }
        }).start();
        mData = new ArrayList<>();
        final HomeBean homeBean = new HomeBean();
        HomeBean.HomeSubBean subBean;
        for (int i = 0; i < 5; i++) {
            subBean = new HomeBean().new HomeSubBean();
            subBean.setDesc("type1--" + i);
            subBean.setType(1);
            mData.add(subBean);
        }
        for (int i = 0; i < 5; i++) {
            subBean = new HomeBean().new HomeSubBean();
            subBean.setDesc("type2--" + i);
            subBean.setType(2);
            mData.add(subBean);
        }

        List<String> list;
        for (int i = 0; i < 5; i++) {
            subBean = new HomeBean().new HomeSubBean();
            list = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                list.add("嵌套--" + j);
            }
            subBean.setStringList(list);
            subBean.setType(3);
            mData.add(subBean);
        }

        for (int i = 0; i < 5; i++) {
            subBean = new HomeBean().new HomeSubBean();
            subBean.setDesc("type4--" + i);
            subBean.setType(4);
            mData.add(subBean);
        }

        homeBean.setLsit(mData);
        rv_home.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MultiItemAdapterSample<HomeBean.HomeSubBean>(this, mData, type);
        final HeaderAndFooterWrapper wrapper = new HeaderAndFooterWrapper(adapter, this);
        // 这里如果Header或者Footer比较复杂，则自定义一个View，在里面做复杂的逻辑和条目的点击事件
        // 这里注意：如果是add同一个View，则在显示的时候只会显示一个，另一个显示空白
        final View header = View.inflate(this, R.layout.header_view, null);
        final View header2 = View.inflate(this, R.layout.header_view2, null);
        final View header3 = View.inflate(this, R.layout.header_view, null);
        wrapper.addHeaderView(header);
        wrapper.addHeaderView(header2);
        wrapper.addFooterView(header);
        wrapper.addFooterView(header3);
        rv_home.setAdapter(wrapper);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Toast.makeText(RvMultiItemActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private MultiItemType<HomeBean.HomeSubBean> type = new MultiItemType<HomeBean.HomeSubBean>() {
        @Override
        public int getLayoutId(int viewType) {
            int layoutId = -1;
            switch (viewType) {
                case 1:
                    layoutId = R.layout.item_type1;
                break;
                case 2:
                    layoutId = R.layout.item_type2;
                break;
                case 3:
                    layoutId = R.layout.item_type3;
                break;
                case 4:
                    layoutId = R.layout.item_type4;
                    break;
            }
            return layoutId;
        }

        @Override
        public int getItemViewType(int position, HomeBean.HomeSubBean item) {
            return item.getType();
        }
    };


    public void add(View view) {

    }

    public void remove(View view) {

    }


}
