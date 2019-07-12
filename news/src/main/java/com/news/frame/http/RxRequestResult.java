package com.news.frame.http;

import android.util.Log;

import com.google.gson.Gson;
import com.news.frame.BaseConfig;
import com.news.frame.base.BaseBean;
import com.news.frame.base.BaseView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 *   Created by boy
 * Data:2019/1/9 0009
 * Desc:
 */

public class RxRequestResult<T extends BaseBean, V extends BaseView<T>> extends DisposableObserver<Response<ResponseBody>> {
    private int position;
    private Class<T> baseBean;

    private V baseView;

    public RxRequestResult(int position, Class<T> baseBean, V view) {
        this.position = position;
        this.baseBean = baseBean;
        this.baseView = view;
    }

    @Override
    public void onNext(Response<ResponseBody> response) {
        baseView.onHideLoading();
        if (response.isSuccessful()) {
            try {
                String result = response.body().string();
                T bean = new Gson().fromJson(result, baseBean);
                switch (bean.code) {
                    case BaseConfig.HTTP_STATE_SUCCESS:
                        baseView.onShowResult(position, bean);
                        break;
                    default:
                        baseView.onShowError(bean.message, bean.code);
                        break;
                }

            } catch (Exception e) {
                baseView.onShowError("数据解析异常", 303);
                e.printStackTrace();
            }

        }else {
            baseView.onShowError("服务器链接失败",response.code());
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            baseView.onHideLoading();
            if (e instanceof SocketTimeoutException) {//请求超时
            } else if (e instanceof ConnectException) {//网络连接超时
                //                ToastManager.showShortToast("网络连接超时");
                baseView.onShowError("服务器地址不正确", position);
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                //                ToastManager.showShortToast("安全证书异常");
                baseView.onShowError("安全证书异常", position);
            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
                    //                    ToastManager.showShortToast("网络异常，请检查您的网络状态");
                    baseView.onShowError("网络异常，请检查您的网络状态", position);
                } else if (code == 404) {
                    //                    ToastManager.showShortToast("请求的地址不存在");
                    baseView.onShowError("请求的地址不存在", position);
                } else {
                    //                    ToastManager.showShortToast("请求失败");
                    baseView.onShowError("请求失败", position);
                }
            } else if (e instanceof UnknownHostException) {//域名解析失败
                //                ToastManager.showShortToast("域名解析失败");
                baseView.onShowError("域名解析失败", position);
            } else {
                //                ToastManager.showShortToast("error:" + e.getMessage());
                baseView.onShowError("error:" + e.getMessage(), position);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
            //            mOnSuccessAndFaultListener.onFault("error:" + e.getMessage());
//            dismissProgressDialog();


        }
    }

    @Override
    public void onComplete() {
        baseView.onHideLoading();
    }
}