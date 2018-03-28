package com.jj.investigation.customebehavior.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.http.DownloadManager;
import com.jj.investigation.customebehavior.utils.NotificationUtil;

public class CustomeViewActivity extends AppCompatActivity implements DownloadManager.ProgressListener {

    public static final String DOWNLOAD_START = "download_start";
    public static final String DOWNLOAD_PAUSE = "download_pause";
    private ProgressBar pb_down;
    private DownloadManager downloadManager;
    private TextView tv_do;
    private NotificationUtil util;
    private int progress = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            util.updataNotification(downloadManager.getInfo().getId(), progress);
            progress++;
            if (progress > 100) {
                handler.removeCallbacksAndMessages(null);
                return;
            }
            sendNotify();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_view);

        pb_down = (ProgressBar) findViewById(R.id.pb_down);
        tv_do = (TextView) findViewById(R.id.tv_do);
        downloadManager = DownloadManager.getInstance();
        downloadManager.setProgressListener(this);
        util = new NotificationUtil(this);
    }

    public void verticalScrollView(View view) {
        startActivity(new Intent(this, VerticalScrollViewActivity.class));
    }

    public void slideMenu(View view) {
        startActivity(new Intent(this, SlideMenuActivity.class));
    }

    public void webView(View view) {
        startActivity(new Intent(this, WebActivity.class));
    }

    public void recyclerview(View view) {
        startActivity(new Intent(this, RvOneItemActivity.class));
    }

    public void multiadpater(View view) {
        startActivity(new Intent(this, RvMultiItemActivity.class));
    }

    public void godown(View view) {
        util.showNotification(downloadManager.getInfo());
//        downloadManager.start(MyConstants.DOWNLOAD_URL);
        sendNotify();
    }

    public void sendNotify() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 50);
    }

    @Override
    public void progressChanged(long read, long contentLength, boolean done) {
        final int progress = (int) (100 * read / contentLength);
        pb_down.setProgress(progress);
        tv_do.setText(pb_down.getProgress() + "%");
        util.updataNotification(downloadManager.getInfo().getId(), progress);
    }
}
