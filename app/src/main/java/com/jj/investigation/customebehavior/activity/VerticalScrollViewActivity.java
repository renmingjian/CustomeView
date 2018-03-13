package com.jj.investigation.customebehavior.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.adapter.DataAdapter;
import com.jj.investigation.customebehavior.utils.StatusBarUtil;
import com.jj.investigation.customebehavior.view.VerticalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示上下滑动的View页面
 * Created by ${R.js} on 2018/3/6.
 */
public class VerticalScrollViewActivity extends AppCompatActivity implements VerticalScrollView.OnRefreshListener {

    private ListView listView;
    private VerticalScrollView verticalScrollView;
    private Toolbar tool_bar;
    private AppBarLayout appbarlayout;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_scroll_iew);

        StatusBarUtil.setStatusBarTranslucent(this);

        listView = (ListView) findViewById(R.id.listView);
        tool_bar = (Toolbar) findViewById(R.id.tool_bar);
        tv = (TextView) findViewById(R.id.tv);
        appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        appbarlayout.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
        verticalScrollView = (VerticalScrollView) findViewById(R.id.verticalScrollView);
//        verticalScrollView.setIsScaleMenuView(false);
        verticalScrollView.setOnScrollListener(new VerticalScrollView.OnScrollListener() {
            @Override
            public void onScroll(int instance, int maxInstance, float alpha) {
                appbarlayout.setAlpha(alpha);
                if (instance <= appbarlayout.getHeight()) {
                    if (!"你爹也是程序员".equals(tool_bar.getTitle()))
                        tool_bar.setTitle("你爹也是程序员");
                } else {
                    if (!"你大爷是程序员".equals(tool_bar.getTitle())) {
                        tool_bar.setTitle("你大爷是程序员");
                    }
                }
                tv.setTranslationY(instance / 2 - maxInstance / 2);
                tv.setScaleX(1 - alpha / 2);
                tv.setScaleY(1 - alpha / 2);
            }
        });
        verticalScrollView.setOnRefreshListener(this);
        initData();
    }

    private void initData() {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item--" + i);
        }
        listView.setAdapter(new DataAdapter(this, list));
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            verticalScrollView.setRefreshState(VerticalScrollView.RefreshState.COMPLETED);
            Toast.makeText(VerticalScrollViewActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
        }
    };
}
