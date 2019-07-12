package com.news.frame.weight.refresh;

import android.view.View;


public interface IHeaderWrapper {

    /**
     * 获取刷新布局
     * @return
     */
    View getHeaderView();

    /**
     * 下拉中
     */
    void pullDown();

    /**
     * 下拉可刷新
     */
    void pullDownReleasable();

    /**
     * 下拉刷新中
     */
    void pullDownRelease();
}
