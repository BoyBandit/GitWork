package com.news.frame.inter.downListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 *   Created by boy
 * Data:2019/1/15 0015
 * Desc:拦截下载日志
 */

public class DownloadInterceptor implements Interceptor {

    private DownloadProgressListener listener;

    public DownloadInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), listener))
                .build();
    }
}
