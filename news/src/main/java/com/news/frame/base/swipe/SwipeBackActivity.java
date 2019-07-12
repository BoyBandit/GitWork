package com.news.frame.base.swipe;

import android.os.Bundle;

import com.news.frame.base.BaseActivity;
import com.news.frame.base.swipe.core.ISwipeBackActivity;
import com.news.frame.base.swipe.core.SwipeBackActivityDelegate;
import com.news.frame.base.swipe.core.SwipeBackLayout;
import com.news.frame.presenter.MvpPresenter;


/**
 *  Created by boy
 * Data:2018/8/13 0013
 *
 * Desc:滑动的Activity
 */

public abstract class SwipeBackActivity<P extends MvpPresenter> extends BaseActivity<P> implements ISwipeBackActivity {
    private SwipeBackActivityDelegate mDelegate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);
        mDelegate = new SwipeBackActivityDelegate(this);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }
    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mDelegate.getSwipeBackLayout();
    }

    /**
     * 是否可滑动
     * @param enable
     */
    @Override
    public void setSwipeBackEnable(boolean enable) {
        mDelegate.setSwipeBackEnable(enable);
    }

    @Override
    public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) {
        mDelegate.setEdgeLevel(edgeLevel);
    }

    @Override
    public void setEdgeLevel(int widthPixel) {
        mDelegate.setEdgeLevel(widthPixel);
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity优先滑动退出;  false: Fragment优先滑动退出
     */
    @Override
    public boolean swipeBackPriority() {
        return mDelegate.swipeBackPriority();
    }
}
