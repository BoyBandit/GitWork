package com.news.frame.inter.downDisable;



import com.news.frame.base.BaseView;
import com.news.frame.bean.DownloadData;
import com.news.frame.inter.downListener.DownloadProgressListener;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by Amuse
 * Data:2019/1/19 0019
 * Desc:
 */

public abstract class BaseDisable<T extends DownloadData,V extends BaseView>   extends DisposableObserver<T> implements DownloadProgressListener {
}
