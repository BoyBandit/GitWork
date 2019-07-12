package com.news.frame.util;

/**
 *   Created by boy
 * Data:2019/1/18 0018
 * Desc:异常检测
 */

public class HttpException extends RuntimeException {

    public static final int NO_DATA = 0x2;

    public HttpException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public HttpException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 转换错误数据
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case NO_DATA:
                message = "无数据";
                break;
            default:
                message = "error";
                break;

        }
        return message;
    }
}