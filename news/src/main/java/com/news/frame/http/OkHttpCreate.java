package com.news.frame.http;

import android.os.Environment;

import com.news.frame.inter.downDisable.BaseDisable;
import com.news.frame.inter.downListener.DownloadInterceptor;
import com.news.frame.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
//import okhttp3.logging.HttpLoggingInterceptor;

/**
 *   Created by boy
 * Data:2019/1/5 0005
 * Desc:
 */

public class OkHttpCreate {

    private static final int DEFAULT_CONNECT_TIMEOUT = 30;//链接超时
    private static final int DEFAULT_WRITE_TIMEOUT = 30;//写入超时
    private static final int DEFAULT_READ_TIMEOUT = 30;//读取超时

    private static OkHttpCreate okHttpClient;
    private OkHttpClient.Builder mHttpBuilder;

    public OkHttpCreate() {

    }

    public static OkHttpCreate getOkHttpInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpCreate();
        }

        return okHttpClient;
    }

    /**
     * 添加头部信息
     *
     * @return
     */
    public OkHttpCreate addHttpHeader() {

        /**
         * 设置头信息
         */
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()

                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")

                        .addHeader("Accept-Encoding", "gzip, deflate")

                        .addHeader("Connection", "keep-alive")

                        .addHeader("Accept", "*/*")

                        .addHeader("Cookie", "add cookies here")

                        .build();

                return chain.proceed(request);
            }
        };
        this.mHttpBuilder.addInterceptor(headerInterceptor);
        return this;
    }

    /***
     * 添加日志文件
     * @return
     */
    public OkHttpCreate addHttpLogger(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.d(message);
            }


        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        return this;
    }




    /**
     * 设置超时时间
     *
     * @return
     */
    public OkHttpCreate setHttpTimeOut(OkHttpClient.Builder builder) {
        builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);

        return this;
    }

    /**
     * 设置缓存路径和大小
     */
    public OkHttpCreate setCache(OkHttpClient.Builder builder) {
        //缓存容量     缓存路径
        //缓存路径和大小
        File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        builder.cache(cache);

        return this;
    }



    public OkHttpClient.Builder getHttpBuilder() {
        if (mHttpBuilder == null) {
            mHttpBuilder = new OkHttpClient.Builder();
        }
        return mHttpBuilder;
    }

    public OkHttpClient.Builder createOkHttpBuilder() {

        getHttpBuilder();
        addHttpLogger(mHttpBuilder);
//        addHttpHeader();
        setHttpTimeOut(mHttpBuilder);
//        mHttpBuilder.cookieJar(new CookieJar() {
//            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>(16);
//
//            @Override
//            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                cookieStore.put(HttpUrl.parse(url.host()), cookies);
//            }
//
//            @Override
//            public List<Cookie> loadForRequest(HttpUrl url) {
//                List<Cookie> cookies = cookieStore.get(url.host());
//                return cookies != null ? cookies : new ArrayList<Cookie>();
//            }
//        });
//        setCache(mHttpBuilder);

//        addHttpHeader();
        return mHttpBuilder;
    }

    public <D extends BaseDisable> OkHttpClient.Builder getHttpDownloadBuilder(long time, D downDisable) {
//        if (mHttpBuilder == null) {
//            mHttpBuilder = new OkHttpClient.Builder();
//        }
////        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        //手动创建一个OkHttpClient并设置超时时间
//        mHttpBuilder.connectTimeout(time, TimeUnit.SECONDS);
//        mHttpBuilder.addInterceptor(new DownloadInterceptor(downDisable));
//        return mHttpBuilder;

        return  new OkHttpClient.Builder().connectTimeout(time, TimeUnit.SECONDS).addInterceptor(new DownloadInterceptor(downDisable));
    }




}
