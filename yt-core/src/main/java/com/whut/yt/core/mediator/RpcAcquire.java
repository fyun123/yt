package com.whut.yt.core.mediator;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 17:10
 * @Description: 获取
 */
public interface RpcAcquire {

    /**
     * Acquire string
     *
     * @param key key
     * @return string
     */
    String acquire(String key);

}
