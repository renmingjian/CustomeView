package com.jj.investigation.customebehavior.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.jj.investigation.customebehavior.utils.MyActivityManager;
import com.jj.investigation.customebehavior.utils.StatusBarUtil;

/**
 * BaseActivity
 * Created by ${R.js} on 2018/3/22.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置无ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置只竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        StatusBarUtil.setStatusBarColor(this, setStatusBarColor());
        // 设置布局
        setContentView(initLayout());
        MyActivityManager.getInstance().add(this);

        mActivity = this;
        initView();
    }

    /**
     * 初始化布局
     * @return Activity的布局ID
     */
    protected abstract int initLayout();

    /**
     * 设置各个页面的状态栏颜色
     * @return 颜色值
     */
    protected abstract int setStatusBarColor();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 设置不需要设置状态栏的颜色
     *
     * 是否设置StatusBar的颜色，绝大部分是要设置的，特殊的不需要设置，例如一个Activity中有多个Fragment的
     * Activity，多个Fragment的状态栏颜色可能不相同，那就只好交给Fragment自己去设置。遇到这样的Activity
     * 需要返回false
     */
    protected void notSetStatusBarColor() {
        StatusBarUtil.setStatusBarTranslucent(this);
    }

}
