package com.news.frame.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.news.frame.base.BaseView;
import com.news.frame.base.Presenter;

import java.lang.ref.WeakReference;

/**
 *  Created by boy
 * Data:2018/6/27 0027
 *
 * Desc:
 */

public abstract class WrapperPresenter<V extends BaseView> implements Presenter<V> {
    private WeakReference<V> viewReference; //MvpView的子类的弱引用
    protected String viewClassName; //类名 Tag

    /**
     * 用来标记取消。
     */
    public Object object = new Object();

    @Override
    public void attachView(V view) {
        // 初始化请求队列，传入的参数是请求并发值。
        viewClassName = view.getClass().getSimpleName();
        viewReference = new WeakReference<>(view);
    }

    /**
     * 检查Activity或者Fragment是否已经绑定到了Presenter层
     *
     * @return 是否已经绑定
     */
    public boolean isViewAttached() {
        return viewReference != null && viewReference.get() != null;
    }


    /**
     * @return 获取实现了MvpView接口的Activity或者Fragment的引用用来实现回调
     */
    public V getView() {
        return viewReference == null ? null : viewReference.get();
    }


    @Override
    public void detachView() {

        if (viewReference != null) {
            viewReference.clear();
            viewReference = null;
        }
    }



    public Context getPresenterContext() {
        return getView() instanceof Context ? (Context) getView() : ((Fragment) getView()).getContext();
    }

    public abstract String getBaseUrl();

    public abstract String getToken();


}
