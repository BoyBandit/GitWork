package com.news.frame.http;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Amuse
 * Data:2019/1/11 0011
 * Desc:
 */

public interface HttpApi {


    /**
     * get请求
     * @param url
     * @return
     */
    @GET
    Observable<Response<ResponseBody>> rxGet(@Url String url);

    /**
     * get请求
     * @param url
     * @param queryMap
     * @return
     */
    @GET
    Observable<Response<ResponseBody>> rxGet(@Url String url, @QueryMap Map<String, Object> queryMap);

    /**
     * get请求
     * @param url
     * @param queryMap
     * @return
     */
    @GET
    Observable<Response<ResponseBody>> rxGet(@Url String url, @QueryMap Map<String, Object> queryMap, @HeaderMap Map<String, Object> headMap);


    /**
     * post请求
     * @param url
     * @return
     */
    @POST
    Observable<Response<ResponseBody>> rxPost(@Url String url);

    /**
     * post请求
     * @param url
     * @return
     */
    @POST
    Observable<Response<ResponseBody>> rxHeadPost(@Url String url, @HeaderMap Map<String, Object> headMap);

    /**
     * post请求
     * @param url
     * @param fieldMap
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<Response<ResponseBody>> rxPost(@Url String url, @FieldMap Map<String, Object> fieldMap);

    /**
     * post请求
     * @param url
     * @param fieldMap
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<Response<ResponseBody>> rxPost(@Url String url, @FieldMap Map<String, Object> fieldMap, @HeaderMap Map<String, Object> headMap);

    /**
     * post请求 加json（body）请求
     * @param url
     * @param fieldMap
     * @param bean
     * @param <T>
     * @return
     */
    @FormUrlEncoded
    @POST
    <T>Observable<Response<ResponseBody>> rxPostBody(@Url String url, @FieldMap Map<String, Object> fieldMap, @Body T bean);

    /**
     * post请求 加json（body）请求
     * @param url
     * @param bean
     * @return
     */
    @POST
    Observable<Response<ResponseBody>> rxPostBody(@Url String url, @Body Object bean);

    /**
     * post请求 加json（body）请求
     * @param url
     * @param bean
     * @param map
     * @return
     */
    @POST
    Observable<Response<ResponseBody>> rxPostBody(@Url String url, @Body Object bean, @HeaderMap Map<String, Object> map);


    /**
     * 单张图片上传
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST
    Observable<Response<ResponseBody>> rxFileUpload(@Url String url, @Part("description") RequestBody description, @Part MultipartBody.Part file);

    /**
     * 单张图片上传
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST
    Observable<Response<ResponseBody>> rxFileUpload(@Url String url, @Part MultipartBody.Part file);



    /**
     * 多张图片上传
     * @param url
     * @param map
     * @return
    */
    @Multipart
    @POST
    Observable<Response<ResponseBody>>  rxFileUploads(@Url String url, @Part("description") RequestBody description, @PartMap Map<String, RequestBody> map);

    /**
     * 多张图片上传
     * @param url
     * @param map
     * @return
     */
    @Multipart
    @POST
    Observable<Response<ResponseBody>>  rxFileUpload(@Url String url, @Part("description") RequestBody description, @PartMap Map<String, MultipartBody.Part> map);

    /**
     * 文件下载
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> rxDownloadFile(@Header("RANGE") String start, @Url String fileUrl);






}


