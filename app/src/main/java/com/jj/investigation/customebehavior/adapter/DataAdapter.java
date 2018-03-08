package com.jj.investigation.customebehavior.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jj.investigation.customebehavior.R;

import java.util.List;

/**
 * Created by ${R.js} on 2018/3/8.
 */

public class DataAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public DataAdapter(Context context, List<String> list) {
        this.context = context;
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
            convertView = View.inflate(context, R.layout.item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(getItem(position));
        return convertView;
    }

    public class ViewHolder {
        TextView tv;
    }
}
