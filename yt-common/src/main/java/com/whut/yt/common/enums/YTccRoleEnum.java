package com.whut.yt.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 18:16
 * @Description: 事务角色
 */
@RequiredArgsConstructor
@Getter
public enum YTccRoleEnum {

    /**
     * TCC事务发起者（分布式事务事务的发起的调用）
     */
    START(1, "发起者"),

    /**
     * TCC事务参与者（分布式事务rpc调用的实现方）
     */
    PARTICIPANT(2, "参与者"),

    /**
     * TCC事务消费者（分布式事务跨进程rpc的调用）
     */
    CONSUMER(3, "消费者"),


    /**
     * TCC事务提供者(本地嵌套事务)
     */
    LOCAL(4,"本地调用");

    private final int code;

    private final String desc;

}
