package com.news.frame.inter.downDisable;

import android.os.Handler;

import com.news.frame.base.download.DownView;
import com.news.frame.bean.DownloadData;
import com.news.frame.bean.DownloadState;
import com.news.frame.presenter.download.DownLoadManager;
import com.news.frame.util.DbSaveUtil;


/**
 * Created by Amuse
 * Data:2019/1/19 0019
 * Desc:
 */

public class ProgressManagerDisable<T extends DownloadData,V extends DownView>  extends BaseDisable<T,V>{
    //弱引用结果回调
//    private SoftReference<HttpDownloadListener> mSubscriberOnNextListener;
    /*下载数据*/
    private DownloadData downInfo;
    private Handler handler;
    private V   view;

    public ProgressManagerDisable(DownloadData<V> downInfo, Handler handler) {
        this.view = downInfo.getListener();
        this.downInfo=downInfo;
        this.handler=handler;
    }


    public void setDownInfo(DownloadData<V> downInfo) {
        this.view = downInfo.getListener();
        this.downInfo=downInfo;
    }
    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        if(getView()!=null){
            getView().onDownStart(downInfo.getPosition());
        }
        downInfo.setState(DownloadState.START);
    }

    @Override
    public void downloadProgress(long read, long count, boolean done) {
        if (downInfo.getCountLength() > count) {
            read = downInfo.getCountLength() - count + read;
        } else {
            downInfo.setCountLength(count);
        }
        downInfo.setReadLength(read);

        if (getView() == null || !downInfo.isUpdateProgress()) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
                if (downInfo.getState() == DownloadState.PAUSE || downInfo.getState() == DownloadState.STOP) return;
                downInfo.setState(DownloadState.DOWNLOADER);
                getView().updateProgress(downInfo.getReadLength(), downInfo.getCountLength(),downInfo);
            }
        });
    }

    @Override
    public void onNext(T t) {
        if (getView()!= null) {
            getView().onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if(getView()!=null){
            getView().onError(e);
        }
        DownLoadManager.getInstance().remove(downInfo);
        downInfo.setState(DownloadState.ERROR);
        DbSaveUtil.getInstance().update(downInfo);
    }

    @Override
    public void onComplete() {
        if(getView()!=null){
            getView().onComplete(downInfo.getPosition());
        }
        DownLoadManager.getInstance().remove(downInfo);
        downInfo.setState(DownloadState.FINISH);
        DbSaveUtil.getInstance().update(downInfo);
    }

    /**
     * @return 获取实现了MvpView接口的Activity或者Fragment的引用用来实现回调
     */
    public V getView() {
        return this.view;
    }
}

