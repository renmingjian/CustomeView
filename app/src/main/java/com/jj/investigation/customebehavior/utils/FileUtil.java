package com.jj.investigation.customebehavior.utils;

import android.util.Log;

import com.jj.investigation.customebehavior.bean.DownloadInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.ResponseBody;

/**
 * Created by ${R.js} on 2018/3/22.
 */

public class FileUtil {

    /**
     * 写入文件
     *
     * @param file
     * @param info
     * @throws IOException
     */
    public static void writeCache(ResponseBody responseBody, File file, DownloadInfo info) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        long allLength;
        if (info.getContentLength() == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = info.getContentLength();
        }
        Log.e("allLength = ", "" + allLength);
        Log.e("path = ", file.getAbsolutePath());
        /**
         * FileChannel 优势：
         多线程并发读写，并发性；
         IO读写性能提高（OS负责），也可引做共享内存，减少IO操作，提升并发性；
         应用crash，保证这部分内容还能写的进去文件。在我们调用channel.write(bytebuffer)之后，
         具体何时写入磁盘、bytebuffer中内容暂存于哪里（os cache）等相关一系列问题，就交由OS本身负责了
         */
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                info.getReadLength(), allLength - info.getReadLength());
        byte[] buffer = new byte[1024 * 8];
        int len;
        int record = 0;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
        if (channelOut != null) {
            channelOut.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }
}
