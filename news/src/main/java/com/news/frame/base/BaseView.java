package com.news.frame.base;

/**
 * Created by Administrator on 2018/1/30.
 * Auther:ruiwen
 */

public interface BaseView<T extends BaseBean> {

    /**
     * 显示loading对话框
     *
     * @param requestType 接口类型
     * @param result 解析成功后返回vo对象
     */
    void onShowResult(int requestType, T result);

    /**
     * 数据加载成功后的回调
     */
    void onBeforeSuccess();

    /**
     * 显示loading对话框
     */
    void onShowLoading();

    /**
     * 隐藏loading对话框
     */
    void onHideLoading();

    /**
     * 显示错误信息
     *
     * @param errorMsg
     */
    void onShowError(String errorMsg, int errorType);
}
