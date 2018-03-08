package com.jj.investigation.customebehavior.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 沉浸式状态栏工具类
 * Created by js on 2018/3/5.
 */

public class StatusBarUtil {
    /**
     * 为我们的 activity 的状态栏设置颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color) {
        // 5.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 直接调用系统提供的方法 setStatusBarColor
            activity.getWindow().setStatusBarColor(color);
        }
        // 4.4 - 5.0 之间  采用一个技巧，首先把他弄成全屏，在状态栏的部分加一个布局
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 首先把他弄成全屏（），在状态栏的部分加一个布局
            // activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // 电量 时间 网络状态 都还在
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 在状态栏的部分加一个布局 setContentView 源码分析，自己加一个布局 (高度是状态栏的高度)
            View view = new View(activity);
            ViewGroup.LayoutParams params = new ViewGroup
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            view.setLayoutParams(params);
            view.setBackgroundColor(color);

            // android:fitsSystemWindows="true" 每个布局都要写
            // DecorView是一个 FrameLayout 布局 , 会加载一个系统的布局（LinearLayout） ,
            // 在系统布局中会有一个 id 为 android.R.id.content 这布局是（RelativeLayout）

            //  http://www.jianshu.com/p/531d1168b3ee
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view);

            // 获取activity中setContentView布局的根布局
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            contentView.setPadding(0, getStatusBarHeight(activity), 0, 0);
            System.out.println("状态栏高度 = " + getStatusBarHeight(activity));
            // View activityView = contentView.getChildAt(0);
            // activityView.setFitsSystemWindows(true);
            // activityView.setPadding(0,getStatusBarHeight(activity),0,0);
        }
    }

    public static View setStatusBarColorAndInflateStatusBar(Activity activity, int color) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // 在状态栏的部分加一个布局 setContentView 源码分析，自己加一个布局 (高度是状态栏的高度)
        View view = new View(activity);
        ViewGroup.LayoutParams params = new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        view.setLayoutParams(params);
        view.setBackgroundColor(color);

        // android:fitsSystemWindows="true" 每个布局都要写
        // DecorView是一个 FrameLayout 布局 , 会加载一个系统的布局（LinearLayout） ,
        // 在系统布局中会有一个 id 为 android.R.id.content 这布局是（RelativeLayout）

        //  http://www.jianshu.com/p/531d1168b3ee
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(view);

        // 获取activity中setContentView布局的根布局
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        contentView.setPadding(0, getStatusBarHeight(activity), 0, 0);
        return view;
    }

    /**
     * 获取状态栏的高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        // 插件式换肤：怎么获取资源的，先获取资源id，根据id获取资源
        Resources resources = activity.getResources();
        int statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android");
        Log.e("TAG", statusBarHeightId + " -> " + resources.getDimensionPixelOffset(statusBarHeightId));
        return resources.getDimensionPixelOffset(statusBarHeightId);
    }

    /**
     * 设置activity全屏
     *
     * @param activity
     */
    public static void setStatusBarTranslucent(Activity activity) {
        // 5.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 修改状态来字体颜色
     */
    public static void setStatusBarTextColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
