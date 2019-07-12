package com.news.frame.inter.downListener;


import com.news.frame.base.download.DownView;
import com.news.frame.bean.DownloadData;

/**
 *   Created by boy
 * Data:2019/1/19 0019
 * Desc:
 */

public abstract class DownListListener implements DownView{
    @Override
    public void onNext(DownloadData downloadData) {

    }

    @Override
    public void onDownStart(int position) {

    }

    @Override
    public void onComplete(int position) {

    }

    @Override
    public void updateProgress(long readLength, long countLength,DownloadData t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onDownPause(int position) {

    }

    @Override
    public void onDownStop(int position) {

    }
}
