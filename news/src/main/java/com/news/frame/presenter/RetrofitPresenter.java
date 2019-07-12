package com.news.frame.presenter;


import com.news.frame.base.BaseView;
import com.news.frame.bean.DownloadData;
import com.news.frame.http.HttpApi;
import com.news.frame.http.OkHttpCreate;
import com.news.frame.inter.downDisable.BaseDisable;
import com.news.frame.util.HttpException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *   Created by boy
 * Data:2019/1/9 0009
 * Desc:
 */

public abstract class RetrofitPresenter<V extends BaseView> extends WrapperPresenter<V>{

    private int RETRY_COUNT = 0;//请求失败重连次数
    private HttpApi httpApi;
    private Map<Object,DisposableObserver> disMap;


    /**
     * 创建自定义域名服务
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createService(final Class<T> service, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//json转换成JavaBean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpCreate.getOkHttpInstance().createOkHttpBuilder().build())
                .build()
                .create(service);
    }

    /**
     * 创建服务
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createApiService(final Class<T> service) {
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(OkHttpCreate.getOkHttpInstance().createOkHttpBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())//json转换成JavaBean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(service);
    }




    /**
     * 设置订阅 和 所在的线程环境
     */
        public <T>void toSubscribe(Observable<T> observable, DisposableObserver<T> disposableObserver,int position) {
            getDisMap().put(position,disposableObserver);
            observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)//请求失败重连次数
                .subscribe(getDisMap().get(position));


    }




    /**
     * 获取注解服务
     * @return
     */
    public HttpApi getApi(){
        if(httpApi==null){
            httpApi= createApiService(HttpApi.class);
        }
        return httpApi;
    }




    /**
     * 设置请求重复次数
     *
     * @return
     */
    public RetrofitPresenter setRetryCount(int count) {
        this.RETRY_COUNT = count;
        return this;
    }






    /**
     * 创建下载服务
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T,D extends BaseDisable> T createApiService(final Class<T> service, long time, D downDisable, String baseUrl) {
        return new Retrofit.Builder()
                .client(OkHttpCreate.getOkHttpInstance().getHttpDownloadBuilder(time, downDisable).build())
                .addConverterFactory(GsonConverterFactory.create())//json转换成JavaBean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(service);
    }


    /**
     * 写入文件
     *
     * @param file
     * @param info
     * @throws IOException
     */
    public void writeCaches(ResponseBody responseBody, File file, DownloadData info) {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                long allLength = 0 == info.getCountLength() ? responseBody.contentLength() : info.getReadLength() + responseBody
                        .contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                        info.getReadLength(), allLength - info.getReadLength());
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    mappedBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
                throw new HttpException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            throw new HttpException(e.getMessage());
        }
    }
    public Map<Object,DisposableObserver> getDisMap(){
        if(disMap==null){
            disMap=new LinkedHashMap<>();
        }
        return disMap;
    }



    /**
     * 取消全部请求
     */
    public void disposeAllRequest(){
        Set<Object> set=getDisMap().keySet();
        for (Object obj:set){
            disposeRequest(obj);
        }
        getDisMap().clear();
        disMap=null;
    }

    /***
     * 取消单个请求
     * @param position
     */
    public void disposeRequest(Object position){
        if(getDisMap().get(position )!=null){
            getDisMap().get(position ).dispose();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        disposeAllRequest();
    }

}
