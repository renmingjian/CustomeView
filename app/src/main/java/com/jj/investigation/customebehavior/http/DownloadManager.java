package com.jj.investigation.customebehavior.http;

import android.os.Environment;
import android.util.Log;

import com.jj.investigation.customebehavior.bean.DownInfo;
import com.jj.investigation.customebehavior.utils.CommonUtils;
import com.jj.investigation.customebehavior.utils.FileUtil;
import com.jj.investigation.customebehavior.utils.MyConstants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 下载管理
 * Created by ${R.js} on 2018/3/22.
 */

public class DownloadManager implements DownloadProgressListener {

    private DownInfo info;
    private ProgressObserver progressObserver;
    private File outFile;
    private Subscription subscribe;

    private DownloadManager() {
        info = new DownInfo();
        outFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "yaoshi.apk");
        info.setSavePath(outFile.getAbsolutePath());
    }

    public static DownloadManager getInstance() {
        return Holder.manager;
    }

    public static class Holder {
        private static DownloadManager manager = new DownloadManager();
    }

    @Override
    public void progress(final long read, final long contentLength, final boolean done) {
        Log.e("progress : ", "read = " + read + "contentLength = " + contentLength + "done = " + done);
        // 该方法仍然是在子线程，如果想要调用进度回调，需要切换到主线程，否则的话，会在子线程更新UI，直接错误
        final int progress = (int) (100 * read / contentLength);
        Observable.just(progress).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                if (progressObserver != null) {
                    progressObserver.progressChanged(read, contentLength, done);
                }
            }
        });
    }

    /**
     * 开始下载
     * @param url
     */
    public void start(String url) {
        info.setUrl(url);
        DownLoadService service;
        final DownloadInterceptor interceptor = new DownloadInterceptor(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //手动创建一个OkHttpClient并设置超时时间
        builder.connectTimeout(8, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(CommonUtils.getBasUrl(MyConstants.DOWNLOAD_URL))
                .build();
        service = retrofit.create(DownLoadService.class);


        /*指定线程*//*失败后的retry配置*//*读取下载写入文件*///写文件(真正写是在这里)
        /*回调线程*//*数据回调*///订阅的时候才会执行上面的方法
        subscribe = service.download("bytes=" + info.getReadLength() + "-", info.getUrl())
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                   /*失败后的retry配置*/
                /*读取下载写入文件*/
                .map(new Func1<ResponseBody, DownInfo>() {
                    @Override
                    public DownInfo call(ResponseBody responseBody) {
                        try {
                            //写文件(真正写是在这里)
                            FileUtil.writeCache(responseBody, new File(info.getSavePath()), info);
                        } catch (IOException e) {
                            Log.e("yichang:", e.toString());
                        }
                        return info;
                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/   //订阅的时候才会执行上面的方法
                .subscribe(new Subscriber<DownInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.e("下载", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("下载", "onError" + e.toString());
                    }

                    @Override
                    public void onNext(DownInfo downInfo) {
                        Log.e("下载", "onNext");
                    }
                });
    }

    /**
     * 暂停下载
     */
    public void pause() {

    }

    public interface ProgressObserver {
        void progressChanged(long read, long contentLength, boolean done);
    }

    public void setProgressObserver(ProgressObserver progressObserver) {
        this.progressObserver = progressObserver;
    }
}
