package com.news.frame.inter.downListener;

/**
 *   Created by boy
 * Data:2019/1/15 0015
 * Desc:下载进度监听
 */

public interface DownloadProgressListener {



    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    void downloadProgress(long read, long count, boolean done);
}
