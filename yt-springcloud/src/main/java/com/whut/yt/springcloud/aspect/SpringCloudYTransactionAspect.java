package com.whut.yt.springcloud.aspect;


import com.whut.yt.core.aspect.YTransactionAspect;
import com.whut.yt.springcloud.interceptor.SpringCloudYTransactionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 15:06
 * @Description: SpringCloud 切面
 */
@Service
public class SpringCloudYTransactionAspect extends YTransactionAspect implements Ordered {


    @Autowired
    public SpringCloudYTransactionAspect(SpringCloudYTransactionInterceptor springCloudYTransactionInterceptor) {
        this.setyTransctionInterceptor(springCloudYTransactionInterceptor);
    }

    @Override
    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE;
        return 10000;
    }
}
