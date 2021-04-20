package com.whut.yt.core.service.impl;

import com.whut.yt.common.enums.YTccActionEnum;
import com.whut.yt.common.enums.YTccRoleEnum;
import com.whut.yt.common.exception.YTccException;
import com.whut.yt.core.constant.YTransactionType;
import com.whut.yt.core.context.YTransactionContext;
import com.whut.yt.core.entity.YTransactionEntity;
import com.whut.yt.core.manager.YTransactionManager;
import com.whut.yt.core.service.YTransactionAspectInvoker;
import com.whut.yt.core.threadlocal.YTransactionThreadLocal;
import com.whut.yt.core.vo.YTransactionMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 17:54
 * @Description: 加入提供者，需要修改SpringCloudYTransactionInterceptor
 */
@Service
@Slf4j
public class YTransactionAspectInvokerImpl implements YTransactionAspectInvoker {


    private YTransactionManager yTransactionManager;

    RabbitTemplate rabbitTemplate;


    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setyTransactionManager(YTransactionManager yTransactionManager) {
        this.yTransactionManager = yTransactionManager;
    }

    /**
     * 事务处理方法
     *
     * @param context 事务上下文
     * @param pjp 切入点
     * @return Handler
     * @throws Throwable Throwable
     */
    @Override
    public Object invoke(final YTransactionContext context, final ProceedingJoinPoint pjp) throws Throwable {
        if (Objects.isNull(context)) {
            return starterYTransactionHandler(pjp);
        } else {
            if (context.getRole() == YTccRoleEnum.LOCAL.getCode()) {
                return localYTransactionHandler(pjp);
            }
//            else if (context.getRole() == YTccRoleEnum.PARTICIPANT.getCode()
//                    || context.getRole() == YTccRoleEnum.START.getCode()) {
//                return participantYTransactionHandler(pjp, context);
//            }
            else if (context.getRole() == YTccRoleEnum.CONSUMER.getCode()) {
                return consumerYTransactionHandler(pjp, context);
            }
        }
        throw new YTccException("找不到事务处理类");
    }

    /**
     * 发起者事务处理类
     *
     * @param point 切入点
     * @return 执行方法原逻辑结果
     * @throws Throwable Throwable
     */
    private Object starterYTransactionHandler(final ProceedingJoinPoint point) throws Throwable {
        Object res = null;
        YTransactionMessage yTransactionMessage = new YTransactionMessage();
        try {
            // 发起事务
            yTransactionManager.creatYTransGroup();
            try {
                // 执行方法原逻辑
                res = point.proceed();
            } catch (Throwable throwable) {
                log.info("服务异常" + YTransactionManager.getCurrentTransaction().getGroupId());
                List<YTransactionEntity> yTransactions = yTransactionManager.getYTransactionsByGroupId(YTransactionManager.getCurrentTransaction().getGroupId());
                if (yTransactions != null && yTransactions.size() > 0) {
                    for (YTransactionEntity yTransaction : yTransactions) {
                        yTransactionMessage.setGroupId(yTransaction.getGroupId());
                        yTransactionMessage.setYTransactionType(YTransactionType.rollback);
                        yTransactionMessage.setYTransactionId(yTransaction.getYTransactionId());
                        rabbitTemplate.convertAndSend("ytransaction-event-exchange", "ytransaction.notice", yTransactionMessage);
                    }
                }
                throw throwable;
            }
            List<YTransactionEntity> yTransactions = yTransactionManager.getYTransactionsByGroupId(YTransactionManager.getCurrentTransaction().getGroupId());
            if (yTransactions != null && yTransactions.size() > 0) {
                for (YTransactionEntity yTransaction : yTransactions) {
                    yTransactionMessage.setGroupId(yTransaction.getGroupId());
                    yTransactionMessage.setYTransactionType(YTransactionType.commit);
                    yTransactionMessage.setYTransactionId(yTransaction.getYTransactionId());
                    System.out.println(yTransactionMessage.getYTransactionId() + "->" + yTransactionMessage.getYTransactionType());
                    rabbitTemplate.convertAndSend("ytransaction-event-exchange", "ytransaction.notice", yTransactionMessage);
                }
            }
        } finally {
            YTransactionThreadLocal instance = YTransactionThreadLocal.getInstance();
            instance.removeCurrentTransactionContext();
            yTransactionManager.removeCurrentTransaction();
        }
        return res;
    }

    /**
     * 事务提供者处理类
     *
     * @param point 切入点
     * @return res
     * @throws Throwable Throwable
     */
    private Object participantYTransactionHandler(final ProceedingJoinPoint point) throws Throwable {
        Object res;
        try {
            // 执行方法原逻辑
            res = point.proceed();
        } catch (Throwable throwable) {
            System.out.println("feign调用异常" + throwable);
            yTransactionManager.updateYTransaction(YTccActionEnum.CANCELING.getCode());
            throw throwable;
        }
        System.out.println("feign调用结束");
        yTransactionManager.updateYTransaction(YTccActionEnum.CONFIRMING.getCode());
        return res;
    }

    /**
     * 消费者处理类
     *
     * @param point 切入点
     * @param context 事务上下文
     * @return res
     * @throws Throwable Throwable
     */
    private Object consumerYTransactionHandler(final ProceedingJoinPoint point, final YTransactionContext context) throws Throwable {
        Object res;
        try {
            // 创建事务
            yTransactionManager.createYTransaction(context);
            try {
                res = point.proceed();
                yTransactionManager.updateYTransaction(YTccActionEnum.CONFIRMING.getCode());
            } catch (Throwable throwable) {
                yTransactionManager.updateYTransaction(YTccActionEnum.CANCELING.getCode());
                throw throwable;
            }
        } finally {
            YTransactionThreadLocal instance = YTransactionThreadLocal.getInstance();
            instance.removeCurrentTransactionContext();
            yTransactionManager.removeCurrentTransaction();
        }
        return res;
    }

    /**
     * 本地事务处理类
     *
     * @param point 切入点
     * @return point.proceed();
     * @throws Throwable Throwable
     */
    private Object localYTransactionHandler(ProceedingJoinPoint point) throws Throwable {
        return point.proceed();
    }
}
