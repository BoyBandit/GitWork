package com.news.frame.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.news.frame.BaseConfig;
import com.news.frame.R;
import com.news.frame.base.swipe.SwipeBackActivity;
import com.news.frame.inter.OnViewHolder;
import com.news.frame.presenter.MvpPresenter;
import com.news.frame.util.ViewHolder;

/**
 *  Created by boy
 * Data:2018/7/16 0016
 *
 * Desc:状态布局
 */

public abstract class BaseStatusActivity<P extends MvpPresenter> extends SwipeBackActivity<P> {


    protected boolean isLoading = false;//是否是第一次加载

    @Override
    protected void loadData(Bundle savedInstanceState, Intent intent) {
        isLoading = true;
    }


    @Override
    public void onShowError(String errorMsg, int errorType) {
        super.onShowError(errorMsg, errorType);
        if (isLoading == true) {
            switch (errorType) {
                case BaseConfig.ERROR_TYPE_NET:
                    if (frameLayout != null && frameLayout.getChildCount() > 0) {
                        View netView = frameLayout.getChildAt(1);
                        if (netView == null) {
                            frameLayout.addView(getNetErrorView(-1, ""), 1);
                        }
                    }
                    break;
            }
        }




    }


    @Override
    public void onBeforeSuccess() {
        super.onBeforeSuccess();
        isLoading = false;
        if (frameLayout != null && frameLayout.getChildCount() > 1) {
            for (int i = 0; i < frameLayout.getChildCount(); i++) {
                if (i == 0) {
                    continue;
                }
                frameLayout.removeViewAt(i);
            }
        }

    }


    /**
     * 实例化对应layoutId的view同时生成ViewHelper
     *
     * @param group    可为null
     * @param layoutId
     * @param listener
     * @return
     */
    protected View getHelperView(ViewGroup group, int layoutId, OnViewHolder listener) {
        ViewHolder helper = new ViewHolder(this.getLayoutInflater().inflate(layoutId, group == null ? null : group instanceof RecyclerView ? (ViewGroup) group.getParent() : group, false));
        if (listener != null) {
            listener.helper(helper);
        }
        return helper.getItemView();
    }


    /**
     * 获取通用空布局
     *
     * @param mRecyclerView
     * @param imageId
     * @param empty
     * @return
     */
    protected View getEmptyView(RecyclerView mRecyclerView, final int imageId, final String empty) {
        return getHelperView(mRecyclerView, R.layout.common_empty, new OnViewHolder() {
            @Override
            public void helper(ViewHolder helper) {
                if (imageId != -1) {
                    helper.setImageResource(R.id.iv_flag, imageId);
                }
                helper.setText(R.id.tv_tip, empty);


            }
        });
    }

    /**
     * 获取网络异常布局
     *
     * @param imageId
     * @param empty
     * @return
     */
    protected View getNetErrorView(final int imageId, final String empty) {
        return getHelperView(null, R.layout.common_net_error, new OnViewHolder() {
            @Override
            public void helper(ViewHolder helper) {
                if (imageId != -1) {
                    helper.setImageResource(R.id.iv_flag, imageId);
                }
                if (!TextUtils.isEmpty(empty)) {
                    helper.setText(R.id.tv_loading, empty);
                }

                helper.setOnClickListener(R.id.tv_loading, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData(null, getIntent());
                    }
                });


            }
        });
    }

}
