package com.whut.yt.common.exception;

/**
 /**
 * @Author: 一大岐
 * @Date: 2021/4/12 19:55
 * @Description: 自定义异常
 */
@SuppressWarnings("all")
public class YTccException extends RuntimeException{

    private static final long serialVersionUID = 4758435008817435088L;

    public YTccException() {
    }

    public YTccException(String message) {
        super(message);
    }

    public YTccException(String message, Throwable cause) {
        super(message, cause);
    }

    public YTccException(Throwable cause) {
        super(cause);
    }

}
