package com.news.frame.bean;


import com.news.frame.base.download.DownView;
import com.news.frame.http.HttpApi;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Amuse
 * Data:2019/1/16 0016
 * Desc:数据实体类
 */
@Entity
public class DownloadData<V extends DownView> {
    @Id
    private long id;
    /*存储位置*/
    private String savePath;
    /*文件总长度*/
    private long countLength;
    /*下载长度*/
    private long readLength;
    /*下载唯一的HttpService*/
    @Transient
    private HttpApi service;
    /*回调监听*/
    @Transient
    private V view;
    /*超时设置*/
    private  int connectonTime=6;
    /*state状态数据库保存*/
    private int stateInte;
    /*url*/
    private String url;
    /*是否需要实时更新下载进度,避免线程的多次切换*/
    private boolean updateProgress;

    private String baseUrl;//储存服务器地址

    private int position;//序列号

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public DownloadData(String url, V view) {
        setUrl(url);
        setListener(view);
    }

    public DownloadData(String url) {
        setUrl(url);
    }

    @Keep
    public DownloadData(long id, String savePath, long countLength, long readLength,
                        int connectonTime, int stateInte, String url) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectonTime = connectonTime;
        this.stateInte = stateInte;
        this.url = url;
    }

    @Keep
    public DownloadData() {
        readLength=0l;
        countLength=0l;
        stateInte=DownloadState.START.getState();
    }

//    @Generated(hash = 1860227052)
    @Keep
    public DownloadData(long id, String savePath, long countLength, long readLength,
                        int connectonTime, int stateInte, String url, boolean updateProgress) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectonTime = connectonTime;
        this.stateInte = stateInte;
        this.url = url;
        this.updateProgress = updateProgress;
    }

    @Generated(hash = 2006723230)
    public DownloadData(long id, String savePath, long countLength, long readLength,
                        int connectonTime, int stateInte, String url, boolean updateProgress,
                        String baseUrl, int position) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectonTime = connectonTime;
        this.stateInte = stateInte;
        this.url = url;
        this.updateProgress = updateProgress;
        this.baseUrl = baseUrl;
        this.position = position;
    }

    public DownloadState getState() {
        switch (getStateInte()){
            case 0:
                return DownloadState.START;
            case 1:
                return DownloadState.DOWNLOADER;
            case 2:
                return DownloadState.PAUSE;
            case 3:
                return DownloadState.STOP;
            case 4:
                return DownloadState.ERROR;
            case 5:
            default:
                return DownloadState.FINISH;
        }
    }

    public boolean isUpdateProgress() {
        return updateProgress;
    }

    public void setUpdateProgress(boolean updateProgress) {
        this.updateProgress = updateProgress;
    }

    public void setState(DownloadState state) {
        setStateInte(state.getState());
    }


    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }

    public V getListener() {
        return view;
    }

    public void setListener(V view) {
        this.view = view;
    }

    public HttpApi getService() {
        return service;
    }

    public void setService(HttpApi service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getConnectonTime() {
        return this.connectonTime;
    }

    public void setConnectonTime(int connectonTime) {
        this.connectonTime = connectonTime;
    }

    public boolean getUpdateProgress() {
        return this.updateProgress;
    }
}
