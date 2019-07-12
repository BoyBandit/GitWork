package com.news.frame.presenter;


import com.news.frame.base.BaseBean;
import com.news.frame.base.BaseView;
import com.news.frame.http.HttpApi;
import com.news.frame.http.RxRequestResult;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Amuse
 * Data:2019/1/11 0011
 * Desc:
 */

public abstract class MvpPresenter<V extends BaseView> extends RetrofitPresenter<V> {

    /**
     * get请求
     * @param position 请求序列号
     * @param url 地址
     * @param bean 请求实体类
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public <T extends BaseBean>void getData(int position, String url, Class<T> bean, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxGet(url), new RxRequestResult<>(position,bean,getView()),position);
    }

    /**
     * get请求
     * @param position
     * @param url
     * @param bean
     * @param objectMap
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void getData(int position, String url, Class<T> bean, Map<String,Object> objectMap, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxGet(url,objectMap), new RxRequestResult<>(position,bean,getView()),position);
    }

    /**
     * 自定义域名get请求
     * @param baseUrl 域名地址
     * @param position
     * @param url
     * @param bean
     * @param objectMap
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void getData(String baseUrl, int position, String url, Class<T> bean, Map<String,Object> objectMap, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxGet(url,objectMap), new RxRequestResult<>(position,bean,getView()),position);
    }
    /**
     * 自定义域名get请求
     * @param baseUrl 域名地址
     * @param position
     * @param url
     * @param bean
     * @param objectMap
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void getData(String baseUrl, int position, String url, Class<T> bean, Map<String,Object> objectMap, Map<String,Object> headMap, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxGet(url,objectMap,headMap), new RxRequestResult<>(position,bean,getView()),position);
    }

    /**
     * 自定义域名get请求
     * @param baseUrl 域名地址
     * @param position
     * @param url
     * @param bean
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void getData(String baseUrl, int position, String url, Class<T> bean, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxGet(url), new RxRequestResult<>(position,bean,getView()),position);
    }



    /**
     * post请求
     * @param position
     * @param url
     * @param bean
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void postData(int position, String url, Class<T> bean, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxHeadPost(url,getHeadMap()), new RxRequestResult<>(position,bean,getView()),position);
    }

    /**
     * post请求
     * @param position
     * @param url
     * @param bean
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void postData(int position, String url, Class<T> bean, Object object, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxHeadPost(url,getHeadMap()), new RxRequestResult<>(position,bean,getView()),position);
    }

    /**
     * post请求
     * @param position
     * @param url
     * @param bean
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void postData(String baseUrl, int position, String url, Class<T> bean, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxPost(url), new RxRequestResult<>(position,bean,getView()),position);
    }


    /**
     * post请求
     * @param position
     * @param url
     * @param bean
     * @param objectMap
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void postData(int position, String url, Class<T> bean, Map<String,Object> objectMap, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxPost(url,objectMap), new RxRequestResult<>(position,bean,getView()),position);
    }

    /**
     * post请求
     * @param position
     * @param url
     * @param bean
     * @param objectMap
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void postData(String baseUrl, int position, String url, Class<T> bean, Map<String,Object> objectMap, Map<String,Object> headMap, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxPost(url,objectMap,headMap), new RxRequestResult<>(position,bean,getView()),position);
    }


    /**
     * post请求 加json数据
     * @param position
     * @param url
     * @param bean
     * @param objectMap
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void postBodyData(int position, String url, Class<T> baseBean, Map<String,Object> objectMap, Class<?> bean, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxPostBody(url,objectMap,bean), new RxRequestResult<>(position,baseBean,getView()),position);
    }

    /**
     * post请求 加json数据
     * @param position
     * @param url
     * @param bean
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void postBodyData(int position, String url, Class<T> baseBean, Object bean, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxPostBody(url,bean,getHeadMap()), new RxRequestResult<>(position,baseBean,getView()),position);
    }
    /**
     * post请求 加json数据
     * @param position
     * @param url
     * @param bean
     * @param isShow 是否需要弹窗
     * @param mapHead 是否需要弹窗添加请求头
     * @param <T>
     */
    public  <T extends BaseBean>void postBodyData(int position, String url, Class<T> baseBean, Object bean, Map<String,Object> mapHead, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxPostBody(url,bean,mapHead), new RxRequestResult<>(position,baseBean,getView()),position);
    }



    /**
     * post请求 加json数据
     * @param position
     * @param url
     * @param bean
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void postBodyData(String baseUrl, int position, String url, Class<T> baseBean, Object bean, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxPostBody(url,bean), new RxRequestResult<>(position,baseBean,getView()),position);
    }

    /**
     * post请求 加json数据
     * @param position
     * @param url
     * @param bean
     * @param isShow 是否需要弹窗
     * @param mapHead 是否需要弹窗添加请求头
     * @param <T>
     */
    public  <T extends BaseBean>void postBodyData(String baseUrl, int position, String url, Class<T> baseBean, Object bean, Map<String,Object> mapHead, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxPostBody(url,bean,mapHead), new RxRequestResult<>(position,baseBean,getView()),position);
    }

    /**
     * 自定义域名post请求 加json数据
     * @param baseUrl 域名地址
     * @param position
     * @param url
     * @param bean
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void postBodyData(String baseUrl, int position, String url, Class<T> baseBean, Class<?> bean, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxPostBody(url,bean), new RxRequestResult<>(position,baseBean,getView()),position);
    }



    /**
     * 文件上传
     * @param position
     * @param url
     * @param bean
     * @param part
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void getUploadFile(int position, String url, Class<T> bean, MultipartBody.Part part, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxFileUpload(url,part), new RxRequestResult<>(position,bean,getView()),position);
    }


    /**
     * 文件上传
     * @param position
     * @param url
     * @param bean
     * @param part
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void getUploadFile(int position, String url, Class<T> bean, RequestBody body, MultipartBody.Part part, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(getApi().rxFileUpload(url,body,part), new RxRequestResult<>(position,bean,getView()),position);
    }


    /**
     * 文件上传
     * @param position
     * @param url
     * @param bean
     * @param part
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void getUploadFile(String baseUrl, int position, String url, Class<T> bean, RequestBody body, MultipartBody.Part part, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxFileUpload(url,body,part), new RxRequestResult<>(position,bean,getView()),position);
    }


    /**
     * 多张图片上传
     * @param position
     * @param url
     * @param bean
     * @param part
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void getUploadFiles(String baseUrl, int position, String url, Class<T> bean, RequestBody body, Map<String,RequestBody> part, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxFileUploads(url,body,part), new RxRequestResult<>(position,bean,getView()),position);
    }


    /**
     * 文件上传
     * @param position
     * @param url
     * @param bean
     * @param part
     * @param isShow 是否需要弹窗
     * @param <T>
     */
    public  <T extends BaseBean>void getUploadFile(String baseUrl, int position, String url, Class<T> bean, MultipartBody.Part part, boolean isShow) {
        if(isShow && getView()!=null){
            getView().onShowLoading();
        }
        toSubscribe(createService(HttpApi.class,baseUrl).rxFileUpload(url,part), new RxRequestResult<>(position,bean,getView()),position);
    }



    private Map<String,Object> getHeadMap(){
        Map<String,Object> headMap=new LinkedHashMap<>();
        headMap.put("Token",getToken());
        return headMap;
    }

}
