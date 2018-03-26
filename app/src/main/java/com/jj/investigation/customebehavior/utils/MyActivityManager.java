package com.jj.investigation.customebehavior.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * 管理栈Activity
 * Created by ${R.js} on 2018/3/22.
 */

public class MyActivityManager {

    private Stack<Activity> mActivityStack;


    private MyActivityManager() {
        mActivityStack = new Stack<>();
    }

    public static MyActivityManager getInstance() {
        return Holder.manager;
    }

    public static class Holder {
        private static MyActivityManager manager = new MyActivityManager();
    }

    /**
     * 添加一个Activity
     *
     * @param activity Activity
     */
    public void add(Activity activity) {
        mActivityStack.add(activity);
    }

    /**
     * 移除指定的Activity
     *
     * @param activity Activity
     */
    public void removeSpecActivity(Activity activity) {
        mActivityStack.remove(activity);
    }

    /**
     * 移除指定的Activity
     *
     * @param clas class
     */
    public void removeSpecActivity(Class<?> clas) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(clas)) {
                mActivityStack.remove(activity);
            }
        }
    }

    /**
     * 销毁指定的Activity
     * @param activity Activity
     */
    public void destorySpecActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            mActivityStack.remove(activity);
        }
    }

    /**
     * 销毁指定的Activity
     * @param clas clas
     */
    public void destorySpecActivity(Class<?> clas) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(clas)) {
                activity.finish();
                mActivityStack.remove(activity);
            }
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void destoryAllActivities() {
        for (Activity activity : mActivityStack) {
            if (activity != null)
                activity.finish();
                mActivityStack.remove(activity);
        }
    }

    /**
     * 退出App
     */
    public void exitApp() {
        destoryAllActivities();
        System.exit(0);
    }
}
