package com.news.frame;

/**
 *   Created by boy
 * Data:2018/12/19 0019
 * Desc:
 */

public class BaseConfig {

    public static String TYPE_DB_NAME="Down_DB";//数据库名称

    public final static int HTTP_STATE_SUCCESS=1;//请求成功
    /**
     * 请求错误类型
     */
    public static final int  ERROR_TYPE_NET=1001;//网络已断开
    public static final int ERROR_TYPE_TIME_OUR=1002;//请求超时
    public static final int ERROR_TYPE_HOST=1003;//未发现指定服务器
    public static final int ERROR_TYPE_URL=1004;//url错误
    public static final  int ERROR_TYPE_EXCEPTION=1005;//未知错误


}
