package com.whut.yt.core.service;

import com.whut.yt.core.context.YTransactionContext;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 17:52
 * @Description: YTransactionAspectInvoker
 */
public interface YTransactionAspectInvoker {

    Object invoke(YTransactionContext context, ProceedingJoinPoint pjp) throws Throwable;

}
