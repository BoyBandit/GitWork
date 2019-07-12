package com.news.frame.presenter.download;

import android.os.Handler;
import android.os.Looper;

import com.news.frame.base.download.DownView;
import com.news.frame.bean.DownloadData;
import com.news.frame.bean.DownloadState;
import com.news.frame.http.HttpApi;
import com.news.frame.inter.downDisable.ProgressManagerDisable;
import com.news.frame.presenter.RetrofitPresenter;
import com.news.frame.util.DbSaveUtil;
import com.news.frame.util.RetryWhenException;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Amuse
 * Data:2019/1/19 0019
 * Desc:
 */

public class DownLoadManager extends RetrofitPresenter<DownView> {
    /*记录下载数据*/
    private Set<DownloadData> downInfos;
    /*回调Disable队列*/
    private HashMap<String, ProgressManagerDisable> subMap;
    /*单利对象*/
    private volatile static DownLoadManager INSTANCE;
    /*数据库类*/
    private DbSaveUtil db;
    /*下载进度回掉主线程*/
    private Handler handler;

    public DownLoadManager() {
        downInfos = new HashSet<>();
        subMap = new HashMap<>();
        db = DbSaveUtil.getInstance();
        handler = new Handler(Looper.getMainLooper());
    }


    /**
     * 获取单例
     *
     * @return
     */
    public static DownLoadManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DownLoadManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DownLoadManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 开始下载
     * @param info 下载相关数据
     * @param baseUrl 下载的服务器设置
     */
    public void startDown(final DownloadData info,String baseUrl) {
        /*正在下载不处理*/
        if (info == null || subMap.get(info.getUrl()) != null) {
            subMap.get(info.getUrl()).setDownInfo(info);
            return;
        }
        /*添加回调处理类*/
        ProgressManagerDisable disable = new ProgressManagerDisable(info, handler);
        /*记录回调sub*/
        subMap.put(info.getUrl(), disable);
        /*获取service，多次请求公用一个sercie*/
        HttpApi httpService;
        if (downInfos.contains(info)) {
            httpService = info.getService();
        } else {

            httpService = createApiService(HttpApi.class, info.getConnectonTime(), disable,baseUrl);
            info.setService(httpService);
            downInfos.add(info);
        }
         /*得到rx对象-上一次下载的位置开始下载*/
        httpService.rxDownloadFile("bytes=" + info.getReadLength() + "-", info.getUrl())
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                   /*失败后的retry配置*/
                .retryWhen(new RetryWhenException())
                /*读取下载写入文件*/
                .map(new Function<ResponseBody, DownloadData>() {
                    @Override
                    public DownloadData apply(ResponseBody responseBody) throws Exception {
                        writeCaches(responseBody, new File(info.getSavePath()), info);
                        return info;
                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(disable);


    }


    /**
     * 停止下载
     */
    public void stopDown(DownloadData info) {
        if (info == null || info.getState() == DownloadState.STOP) return;
        info.setState(DownloadState.STOP);
        info.getListener().onDownStop(info.getPosition());
        if (subMap.containsKey(info.getUrl())) {
            ProgressManagerDisable subscriber = subMap.get(info.getUrl());
            subscriber.dispose();
            subMap.remove(info.getUrl());
        }
        /*保存数据库信息和本地文件*/
        db.save(info);
    }


    /**
     * 暂停下载
     *
     * @param info
     */
    public void pause(DownloadData info) {
        if (info == null) return;
        info.setState(DownloadState.PAUSE);
        info.getListener().onDownPause(info.getPosition());
        if (subMap.containsKey(info.getUrl())) {
            ProgressManagerDisable subscriber = subMap.get(info.getUrl());
            subscriber.dispose();
            subMap.remove(info.getUrl());
        }
        /*这里需要讲info信息写入到数据中，可自由扩展，用自己项目的数据库*/
        db.update(info);
    }

    /**
     * 停止全部下载
     */
    public void stopAllDown() {
        for (DownloadData downInfo : downInfos) {
            stopDown(downInfo);
        }
        subMap.clear();
        downInfos.clear();
    }

    /**
     * 暂停全部下载
     */
    public void pauseAll() {
        for (DownloadData downInfo : downInfos) {
            pause(downInfo);
        }
        subMap.clear();
        downInfos.clear();
    }


    /**
     * 返回全部正在下载的数据
     *
     * @return
     */
    public Set<DownloadData> getAllData() {
        return downInfos;
    }

    /**
     * 移除下载数据
     *
     * @param info
     */
    public void remove(DownloadData info) {
        subMap.remove(info.getUrl());
        downInfos.remove(info);
    }

    /**
     * 避免内存溢出
     * ====回收rxJava沉淀的数据
     */
    public void clearDisable(DownloadData downloadData) {
        if (downloadData != null) {

                updateDownload(downloadData);

        }

    }
    /**
     * 避免内存溢出
     * ====回收rxJava沉淀的数据
     */
    public void clearListDisable(List<DownloadData> list) {
        if (list != null && list.size() > 0) {
            for (DownloadData data : list) {
                updateDownload(data);
            }
        }

    }

    public void updateDownload(DownloadData info) {
        if (info == null) return;
        info.setState(DownloadState.STOP);
        info.getListener().onDownStop(info.getPosition());
        if (subMap.containsKey(info.getUrl())) {
            ProgressManagerDisable subscriber = subMap.get(info.getUrl());
            subscriber.dispose();
            subMap.remove(info.getUrl());
        }
        /*保存数据库信息和本地文件*/
        db.update(info);
    }


    @Override
    public void detachView() {
        super.detachView();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public String getBaseUrl() {
        return "";
    }

    @Override
    public String getToken() {
        return "";
    }


}