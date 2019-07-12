package com.news.frame.base.download;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.news.frame.R;
import com.news.frame.base.BaseWrapperActivity;
import com.news.frame.dialog.LoadingDialog;
import com.news.frame.presenter.WrapperPresenter;
import com.news.frame.presenter.download.DownLoadPresenter;

import java.lang.reflect.ParameterizedType;

/**
 * @author Auser
 * @create 2019-04-03
 * @Desc:下载基类
 */

public abstract class BaseDownloadActivity <P extends DownLoadPresenter> extends BaseWrapperActivity implements DownView {
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<? extends WrapperPresenter> presenterClass = (Class<? extends WrapperPresenter>) type.getActualTypeArguments()[0];
        try {
            this.mPresenter = (P) presenterClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        mPresenter.attachView(this);
        mLoadingDialog = createLoadingDialog();
        loadData(savedInstanceState, getIntent());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoadingDialog = null;
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    public void onShowLoading() {
        if (!isFinishing() && mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void onHideLoading() {
        if (!isFinishing() && mLoadingDialog != null && mLoadingDialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onBeforeSuccess() {

    }


    @Override
    public void onShowError(String errorMsg, int errorType) {

    }


    protected LoadingDialog createLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, R.style.Alert_Dialog_Style);
            mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        return mLoadingDialog;
    }

    /**
     * 从intent中获取请求参数，初始化vo对象，并发送请求
     *
     * @param savedInstanceState
     * @param intent
     */
    protected abstract void loadData(Bundle savedInstanceState, Intent intent);
}
