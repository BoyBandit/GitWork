package com.news.frame.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.news.frame.R;
import com.news.frame.inter.OnViewHolder;
import com.news.frame.presenter.MvpPresenter;
import com.news.frame.util.ViewHolder;


/**
 *  Created by boy
 * Data:2018/7/13 0013
 *
 * Desc:添加状态布局
 * https://www.jianshu.com/p/7ab318b0d3ba
 */

public abstract class BaseFragment<P extends MvpPresenter> extends BaseWrapperFragment<P> {
    /**
     * 实例化对应layoutId的view同时生成ViewHelper
     *
     * @param group    可为null
     * @param layoutId
     * @param listener
     * @return
     */
    protected View getHelperView(ViewGroup group, int layoutId, OnViewHolder listener) {
        ViewHolder helper = new ViewHolder(getActivity().getLayoutInflater().inflate(layoutId, group == null ? null : group instanceof RecyclerView ? (ViewGroup) group.getParent() : group, false));
        if (listener != null) {
            listener.helper(helper);
        }
        return helper.getItemView();
    }

    /**
     * 通过context 实例化对应layoutId的view同时生成ViewHelper  使用此方法 需要考虑context的生命周期 避免内存泄漏
     *
     * @param context
     * @param group
     * @param layoutId
     * @param listener
     * @return
     */
    protected View getHelperView(Context context, ViewGroup group, int layoutId, OnViewHolder listener) {
        ViewHolder helper = new ViewHolder(LayoutInflater.from(context).inflate(layoutId, group == null ? null : group instanceof RecyclerView ? (ViewGroup) group.getParent() : group, false));
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

}
