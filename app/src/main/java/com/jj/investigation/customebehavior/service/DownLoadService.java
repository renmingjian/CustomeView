package com.jj.investigation.customebehavior.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ${R.js} on 2018/3/28.
 */

public class DownLoadService extends Service {

    public static final String DOWNLOAD_START = "download_start";
    public static final String DOWNLOAD_PAUSE = "download_pause";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
