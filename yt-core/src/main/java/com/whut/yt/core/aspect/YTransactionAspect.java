package com.whut.yt.core.aspect;

import com.whut.yt.core.interceptor.YTransctionInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 14:53
 * @Description:
 */
@Aspect
public class YTransactionAspect{

    private YTransctionInterceptor yTransctionInterceptor;

    /**
     * 设置拦截后的处理器
     *
     * @param yTransctionInterceptor yTransctionInterceptor
     */
    public void setyTransctionInterceptor(final YTransctionInterceptor yTransctionInterceptor) {
        this.yTransctionInterceptor = yTransctionInterceptor;
    }

    /**
     * 拦截YTransaction注解
     */
    @Pointcut("@annotation(com.whut.yt.core.annotation.YTransaction)")
    public void interceptPointcut(){

    }

    /**
     * 拦截处理方法
     *
     * @param pjp 切入点
     * @return interceptMethod
     * @throws Throwable Throwable
     */
    @Around("interceptPointcut()")
    public Object interceptPointcutHandleMethod(final ProceedingJoinPoint pjp) throws Throwable {

        return yTransctionInterceptor.interceptMethod(pjp);
    }
}
