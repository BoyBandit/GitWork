package com.news.frame.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.news.frame.R;
import com.news.frame.inter.OnViewHolder;
import com.news.frame.util.ViewHolder;

import java.lang.ref.WeakReference;

/**
 *   Created by boy
 * Data:2018/12/28 0028
 * Desc:dialog 基类
 */

public abstract class BaseDialog extends Dialog implements OnViewHolder {
    private WeakReference<Context> viewReference; //MvpView的子类的弱引用
    protected ViewHolder helper;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.AlertTipsDialogTheme);
        viewReference = new WeakReference<>(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        viewReference = new WeakReference<>(context);
        setContentView(getHelperView(null, getViewLayout(), this));
    }


    public abstract int getViewLayout();

    /**
     * 实例化对应layoutId的view同时生成ViewHelper
     *
     * @param group    可为null
     * @param layoutId
     * @param listener
     * @return
     */
    protected View getHelperView(ViewGroup group, int layoutId, OnViewHolder listener) {
        helper = new ViewHolder(LayoutInflater.from(getDialogContext()).inflate(layoutId, group, false));
        if (listener != null) {
            listener.helper(helper);
        }
        return helper.getItemView();
    }


    /**
     * 设置参数的参考实现
     *
     * @param width
     * @param height
     * @param gravity
     */
    protected void setDialogParams(int width, int height, int gravity) {
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        window.setGravity(gravity);
        window.setAttributes(params);
    }

    @Override
    public void helper(ViewHolder helper) {

    }


    public Context getDialogContext() {
        return viewReference == null ? null : viewReference.get();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (viewReference != null) {
            viewReference.clear();
            viewReference = null;
        }

    }
}
