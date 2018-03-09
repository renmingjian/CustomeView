package com.jj.investigation.customebehavior.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jj.investigation.customebehavior.R;

/**
 *
 * Created by ${R.js} on 2018/3/9.
 */

public class GroupFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_group, container, false);
        } else {
            if (view.getParent() != null) {
                ((ViewGroup)view.getParent()).removeView(view);
            }
        }
        return view;
    }


}
