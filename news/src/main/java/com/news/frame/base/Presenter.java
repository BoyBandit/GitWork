package com.news.frame.base;

/**
 *  Created by boy Administrator on 2018/1/30.
 * Auther:
 */

public interface Presenter<V> {
    /**
     * Presenter与View建立连接
     *
     * @param mvpView 与此Presenter相对应的View
     */
    void attachView(V mvpView);

    /**
     * Presenter与View连接断开
     */
    void detachView();
}

