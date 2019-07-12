package com.news.frame.base.download;

import com.news.frame.base.BaseView;
import com.news.frame.bean.DownloadData;

/**
 * @author Auser
 * @create 2019-04-03
 * @Desc:
 */

public interface DownView<T extends DownloadData> extends BaseView{
    /**
     * 成功后回调方法
     *
     * @param t
     */
    void onNext(T t);

    /**
     * 开始下载
     */
    void onDownStart(int position);

    /**
     * 完成下载
     */
    void onComplete(int position);


    /**
     * 下载进度
     *
     * @param readLength
     * @param countLength
     */
    void updateProgress(long readLength, long countLength, T t);

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     *
     * @param e
     */
    void onError(Throwable e);

    /**
     * 暂停下载
     */
    void onDownPause(int position);

    /**
     * 停止下载销毁
     */
    void onDownStop(int position);
}
