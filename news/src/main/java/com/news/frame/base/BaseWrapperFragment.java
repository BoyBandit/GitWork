package com.news.frame.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.news.frame.R;
import com.news.frame.dialog.LoadingDialog;
import com.news.frame.presenter.MvpPresenter;
import com.news.frame.presenter.WrapperPresenter;
import com.news.frame.weight.ToastView;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 *  Created by boy
 * Data:2018/7/13 0013
 *
 * Desc:
 */

public abstract class BaseWrapperFragment<P extends MvpPresenter> extends SupportFragment implements BaseView {
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;
    private View contentView;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<? extends WrapperPresenter> presenterClass = (Class<? extends WrapperPresenter>) type.getActualTypeArguments()[0];
        try {
            this.mPresenter = (P) presenterClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        mPresenter.attachView(this);
        mLoadingDialog = createLoadingDialog();
    }


    /*
  * @param inflater
  * @param container
  * @param savedInstanceState
  * @return
          */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getViewLayout(), container, false);
        return contentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initView(savedInstanceState);
        loadData(savedInstanceState);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        if (isAdded() && mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void onHideLoading() {
        if (isAdded() && mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onBeforeSuccess() {

    }


    @Override
    public void onShowError(String errorMsg, int errorType) {

    }






    /**
     * @return 布局resourceId
     */
    public abstract int getViewLayout();

    /**
     * 初始化View。或者其他view级第三方控件的初始化,及相关点击事件的绑定
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    protected LoadingDialog createLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity(), R.style.Alert_Dialog_Style);
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
     */
    protected abstract void loadData(Bundle savedInstanceState);


    protected void showToast(String msg) {
        ToastView.getInstance(_mActivity).showToastContent(msg, Toast.LENGTH_SHORT);

    }


}
