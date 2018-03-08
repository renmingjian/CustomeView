package com.jj.investigation.customebehavior;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jj.investigation.customebehavior.utils.StatusBarUtil;
import com.jj.investigation.customebehavior.view.VerticalScrollView;

import java.util.ArrayList;
import java.util.List;

public class SlideMenuActivity extends AppCompatActivity {

    private ListView listView;
    private VerticalScrollView verticalScrollView;
    private Toolbar tool_bar;
    private AppBarLayout appbarlayout;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);

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
                    if (!"我就是你爹".equals(tool_bar.getTitle()))
                        tool_bar.setTitle("我就是你爹");
                } else {
                    if (!"你大爷".equals(tool_bar.getTitle())) {
                        tool_bar.setTitle("你大爷");
                    }
                }
                tv.setTranslationY(instance / 2 - maxInstance / 2);
//                tv.setTranslationX(0 - 40);
                tv.setScaleX(1 - alpha / 2);
                tv.setScaleY(1 - alpha / 2);
            }
        });
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("item--" + i);
        }
        listView.setAdapter(new DataAdapter(list));
    }

    public class DataAdapter extends BaseAdapter {

        private List<String> list;

        public DataAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (holder == null) {
                holder = new ViewHolder();
                convertView = View.inflate(SlideMenuActivity.this, R.layout.item, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(getItem(position));
            return convertView;
        }
    }

    public class ViewHolder {
        TextView tv;
    }
}
