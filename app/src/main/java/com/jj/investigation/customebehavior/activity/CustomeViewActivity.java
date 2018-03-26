package com.jj.investigation.customebehavior.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jj.investigation.customebehavior.R;
import com.jj.investigation.customebehavior.http.DownloadManager;
import com.jj.investigation.customebehavior.utils.MyConstants;

public class CustomeViewActivity extends AppCompatActivity implements DownloadManager.ProgressObserver {

    private ProgressBar pb_down;
    private DownloadManager downloadManager;
    private TextView tv_do;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_view);

        pb_down = (ProgressBar) findViewById(R.id.pb_down);
        tv_do = (TextView) findViewById(R.id.tv_do);
        downloadManager = DownloadManager.getInstance();
        downloadManager.setProgressObserver(this);
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
        startActivity(new Intent(this, WebActivity.class));
    }

    public void godown(View view) {
        downloadManager.start(MyConstants.DOWNLOAD_URL);
    }


    @Override
    public void progressChanged(long read, long contentLength, boolean done) {
        pb_down.setProgress((int) (100 * read / contentLength));
        tv_do.setText(pb_down.getProgress() + "%");
    }
}
