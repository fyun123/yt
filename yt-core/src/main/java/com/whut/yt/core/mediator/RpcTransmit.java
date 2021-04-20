package com.whut.yt.core.mediator;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 17:11
 * @Description:
 */
public interface RpcTransmit {

    /**
     * Transmit
     *
     * @param key key
     * @param value value
     */
    void transmit(String key, String value);

}
