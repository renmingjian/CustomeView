package com.jj.investigation.customebehavior.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.adapter.DataAdapter;
import com.jj.investigation.customebehavior.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by ${R.js} on 2018/3/8.
 */

public class SlideMenuActivity extends AppCompatActivity {

    private ListView lv_menu;
    private ListView lv_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);
        StatusBarUtil.setStatusBarTranslucent(this);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        lv_main = (ListView) findViewById(R.id.lv_main);
        initData();
    }

    private void initData() {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item--" + i);
        }
        lv_menu.setAdapter(new DataAdapter(this, list));
        lv_main.setAdapter(new DataAdapter(this, list));

        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SlideMenuActivity.this, "点击事件" + position, Toast.LENGTH_SHORT).show();
            }
        });

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SlideMenuActivity.this, "点击事件" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
