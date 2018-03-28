package com.jj.investigation.customebehavior.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.adapter.OneItemAdapterSample;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示RecyclerView
 * Created by ${R.js} on 2018/3/21.
 */

public class RvOneItemActivity extends BaseActivity {

    private RecyclerView rv_home;
    private List<String> mData;
    private OneItemAdapterSample adapter;

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
        mData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mData.add("item--" + i);
        }
        Log.e("initData", "");
        rv_home.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OneItemAdapterSample(this, R.layout.item_home, mData);
        rv_home.setAdapter(adapter);
    }


    public void add(View view) {
        adapter.addData("我曹了");
    }

    public void remove(View view) {
        adapter.removeData("item--0");

    }


}
