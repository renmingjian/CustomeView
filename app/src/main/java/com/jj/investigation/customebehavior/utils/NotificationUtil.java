package com.jj.investigation.customebehavior.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.View;
import android.widget.RemoteViews;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.activity.CustomeViewActivity;
import com.jj.investigation.customebehavior.activity.MainActivity;
import com.jj.investigation.customebehavior.bean.DownloadInfo;
import com.jj.investigation.customebehavior.service.DownLoadService;

/**
 * 通知工具类
 * Created by ${R.js} on 2018/3/28.
 */

public class NotificationUtil {

    private Context mContext;
    private NotificationManager mNotificationManager;
    /* 以下载文件的ID作为key，可能同时下载多个文件，需要把所有的下载通知都存储起来 */
    private SparseArray<Notification> mNotifications;
    private DownloadInfo mInfo;

    public NotificationUtil(Context context) {
        this.mContext = context;
        // 获取通知系统服务
        mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifications = new SparseArray<>();
    }

    /**
     * 显示通知
     */
    public void showNotification(DownloadInfo info) {
        this.mInfo = info;
        // 如果集合中没有这个通知，再进行显示，如果集合中有这个通知，则说明已经显示了
        if (mNotifications.get(info.getId()) == null) {
            // 创建通知对象
            final Notification notification = new Notification();
            // 设置滚动文字
            notification.tickerText = info.getFileName();
            // 设置显示时间
            notification.when = System.currentTimeMillis();
            // 设置图标
            notification.icon = R.drawable.ic_group;
            // 设置通知的特性
            notification.flags = Notification.FLAG_AUTO_CANCEL; // 点击了通知栏的内容之后会自动消失
            // 设置点击通知栏之后的一些操作--跳转Activity
            final Intent intent = new Intent(mContext, MainActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
            notification.contentIntent = pendingIntent;
            // 创建远程视图
            final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                    R.layout.item_notification);
            // 设置点击按钮的操作
            final Intent btnStartIntent = new Intent(mContext, CustomeViewActivity.class);
            btnStartIntent.setAction(CustomeViewActivity.DOWNLOAD_START);
            btnStartIntent.putExtra("downInfo", info);
            final PendingIntent btnStart = PendingIntent.getService(mContext, 0, btnStartIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.btn_download, btnStart);
            remoteViews.setTextViewText(R.id.tv_notify, info.getFileName());

            final Intent btnPauseIntent = new Intent(mContext, CustomeViewActivity.class);
            btnPauseIntent.setAction(DownLoadService.DOWNLOAD_PAUSE);
            btnPauseIntent.putExtra("downInfo", info);
            final PendingIntent btnPause = PendingIntent.getService(mContext, 0, btnPauseIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.btn_pause, btnPause);

            // 把RemoteViews设置到Notification中
            notification.contentView = remoteViews;
            // 发送通知，显示到通知栏上
            mNotificationManager.notify(info.getId(), notification);
            mNotifications.put(info.getId(), notification);
        }
    }

    /**
     * 取消通知
     *
     * @param id 文件的ID
     */
    public void cancelNotification(int id) {
        mNotificationManager.cancel(id);
        mNotifications.remove(id);
    }

    /**
     * 更新进度
     *
     * @param id
     * @param progress
     */
    public void updataNotification(int id, int progress) {
        final Notification notification = mNotifications.get(id);
        if (notification != null) {
            notification.contentView.setProgressBar(R.id.pb_notify, 100, progress, false);
            notification.contentView.setTextViewText(R.id.tv_progress, progress + "%");
            mNotificationManager.notify(id, notification);
            if (progress == 100) {
                notification.contentView.setViewVisibility(R.id.btn_download, View.GONE);
                notification.contentView.setViewVisibility(R.id.btn_pause, View.GONE);
            }
        }
    }
}
