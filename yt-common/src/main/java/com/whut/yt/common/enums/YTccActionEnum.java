package com.whut.yt.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 16:26
 * @Description: 事务动作阶段
 */
@RequiredArgsConstructor
@Getter
public enum YTccActionEnum {

    /**
     * 准备阶段
     */
    PRE_TRY(0, "开始执行try"),

    /**
     * Try 阶段
     */
    TRYING(1, "try阶段完成"),

    /**
     * Confirm 阶段
     */
    CONFIRMING(2, "confirm阶段"),

    /**
     * Cancel阶段
     */
    CANCELING(3, "cancel阶段");

    private final int code;

    private final String desc;

    public static YTccActionEnum acquireByCode(final int code) {
        return Arrays.stream(YTccActionEnum.values())
                .filter(v -> Objects.equals(v.getCode(), code))
                .findFirst().orElse(YTccActionEnum.TRYING);
    }
}
