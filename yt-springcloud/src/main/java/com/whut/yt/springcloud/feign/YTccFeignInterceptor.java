package com.whut.yt.springcloud.feign;

import com.whut.yt.core.mediator.RpcMediator;
import com.whut.yt.core.threadlocal.YTransactionThreadLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 14:41
 * @Description: 将事务上下文添加到feign请求头
 */
@Service
public class YTccFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        RpcMediator.getInstance().transmit(requestTemplate::header, YTransactionThreadLocal.getInstance().getCurrentTransactionContext());
    }
}
