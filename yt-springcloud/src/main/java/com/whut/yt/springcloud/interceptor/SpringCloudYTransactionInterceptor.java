package com.whut.yt.springcloud.interceptor;


import com.whut.yt.common.enums.YTccRoleEnum;
import com.whut.yt.core.interceptor.YTransctionInterceptor;
import com.whut.yt.core.context.YTransactionContext;
import com.whut.yt.core.mediator.RpcMediator;
import com.whut.yt.core.service.YTransactionAspectInvoker;
import com.whut.yt.core.threadlocal.YTransactionThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 15:08
 * @Description: SpringCloud feign拦截器
 */
@Service
@Slf4j
public class SpringCloudYTransactionInterceptor implements YTransctionInterceptor {


    private YTransactionAspectInvoker yTransactionAspectInvoker;

    @Autowired
    public SpringCloudYTransactionInterceptor(final YTransactionAspectInvoker yTransactionAspectInvoker){
        this.yTransactionAspectInvoker = yTransactionAspectInvoker;
    }

    /**
     * 传播事务上下文
     *
     * @param pjp 切入点
     * @return invoke
     * @throws Throwable Throwable
     */
    @Override
    public Object interceptMethod(ProceedingJoinPoint pjp) throws Throwable {
        final YTransactionContext yTransactionContext = YTransactionThreadLocal.getInstance().getCurrentTransactionContext();
        if (Objects.nonNull(yTransactionContext)){
            // yTransactionContext不为空
            if (yTransactionContext.getRole() == YTccRoleEnum.START.getCode()){
                yTransactionContext.setRole(YTccRoleEnum.CONSUMER.getCode());
//                yTransactionContext.setRole(YTccRoleEnum.PARTICIPANT.getCode());
            }
        } else {
            try {
                final RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
                YTransactionContext transactionContext = RpcMediator.getInstance().acquire(requestAttribute -> {
                    HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                    return request.getHeader(requestAttribute);
                });
                if(transactionContext != null){
                    log.info("feign传播上下文，事务组id"+transactionContext.getTransGroupId());
                    transactionContext.setRole(YTccRoleEnum.CONSUMER.getCode());
                    YTransactionThreadLocal.getInstance().setCurrentTransactionContext(transactionContext);
                    return yTransactionAspectInvoker.invoke(transactionContext, pjp);
                }
            }catch (IllegalStateException e){

            }
        }
        return yTransactionAspectInvoker.invoke(yTransactionContext, pjp);
    }
}
