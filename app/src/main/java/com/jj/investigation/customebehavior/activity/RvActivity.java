package com.jj.investigation.customebehavior.activity;

import android.support.v7.widget.RecyclerView;

import com.jj.investigation.customebehavior.R;

/**
 * 显示RecyclerView
 * Created by ${R.js} on 2018/3/21.
 */

public class RvActivity extends BaseActivity {

    private RecyclerView rv_home;

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
}
