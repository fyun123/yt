package com.whut.yt.core.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 14:56
 * @Description: 切面处理器
 */
@FunctionalInterface
public interface YTransctionInterceptor {

    /**
     * 处理切点接口类
     *
     * @param pjp 切入点
     * @return interceptMethod
     * @throws Throwable Throwable
     */
    Object interceptMethod(ProceedingJoinPoint pjp) throws Throwable;

}
