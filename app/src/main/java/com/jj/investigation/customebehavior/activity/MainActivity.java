package com.jj.investigation.customebehavior.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jj.investigation.customebehavior.BehaviroAdapter;
import com.jj.investigation.customebehavior.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        AppBarLayout appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        appbarlayout.setAlpha(0);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomeViewActivity.class));
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BehaviroAdapter adapter = new BehaviroAdapter(this);

        mRecyclerView.setAdapter(adapter);

    }
}
