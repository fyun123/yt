package com.whut.yt.core.manager;


import com.whut.yt.common.enums.YTccActionEnum;
import com.whut.yt.common.enums.YTccRoleEnum;
import com.whut.yt.core.context.YTransactionContext;
import com.whut.yt.core.entity.YTransactionEntity;
import com.whut.yt.core.store.RedisStore.RedisService;
import com.whut.yt.core.threadlocal.YTransactionThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: 一大岐
 * @Date: 2021/4/12 21:55
 * @Description: 事务管理器
 */
@Component
@Slf4j
public final class YTransactionManager {

    @Autowired
    RedisService redisService;

    private static final YTransactionManager Y_TRANSACTION_MANAGER = new YTransactionManager();

    /**
     * 当前事务
     */
    private static ThreadLocal<YTransactionEntity> CURRENT_TRANSACTION = new ThreadLocal<>();

    /**
     * 本地事务
     */
    public static Map<String, YTransactionEntity> TRANSACTION_MAP = new HashMap<>();

    public static YTransactionManager getInstance() {
        return Y_TRANSACTION_MANAGER;
    }

    /**
     * 更新事务上下文
     *
     * @param code 事务阶段（准备、提交、回滚）
     */
    public void updateYTransaction(final int code) {
        YTransactionThreadLocal.getInstance().getCurrentTransactionContext().setAction(code);
    }

    /**
     * 根据groupId获取本地事务
     *
     * @param groupId 事务组id
     * @return 事务
     */
    public static YTransactionEntity getYTTransaction(String groupId) {
        return TRANSACTION_MAP.get(groupId);
    }

    /**
     * 获取本地事务
     *
     * @return 当前事务
     */
    public static YTransactionEntity getCurrentTransaction() {
        return CURRENT_TRANSACTION.get();
    }

    /**
     * 从本地移除事务
     */
    public void removeCurrentTransaction() {
        CURRENT_TRANSACTION.remove();
    }

    /**
     * 创建事务组和根事务
     */
    public void creatYTransGroup(){
        YTransactionThreadLocal instance = YTransactionThreadLocal.getInstance();
        String groupId = UUID.randomUUID().toString().replace("-","");
        String transId = UUID.randomUUID().toString().replace("-","");
        YTransactionEntity root = new YTransactionEntity(groupId, transId);
        YTransactionContext context = new YTransactionContext();
        context.setTransGroupId(groupId);
        context.setAction(YTccActionEnum.PRE_TRY.getCode());
        context.setRole(YTccRoleEnum.START.getCode());
        //  支持事务
        instance.setCurrentTransactionContext(context);
        CURRENT_TRANSACTION.set(root);
        TRANSACTION_MAP.put(groupId,root);
        // 持久化
        redisService.save(root);
        log.info("创建根事务:"+transId+"事务组"+groupId);
    }

    /**
     * 创建分支事务
     *
     * @param yTransactionContext 事务上下文
     */
    public void createYTransaction(final YTransactionContext yTransactionContext){
        YTransactionThreadLocal instance = YTransactionThreadLocal.getInstance();
        String transId = UUID.randomUUID().toString().replace("-","");
        YTransactionEntity branch = new YTransactionEntity(yTransactionContext.getTransGroupId(), transId);
        CURRENT_TRANSACTION.set(branch);
        TRANSACTION_MAP.put(yTransactionContext.getTransGroupId(),branch);
        redisService.save(branch);
        log.info("创建分支事务:"+transId+"事务组"+instance.getCurrentTransactionContext().getTransGroupId());
    }

    /**
     * 根据事务组id获取事务
     *
     * @param groupId 事务组id
     * @return 事务组内的所有事务
     */
    public List<YTransactionEntity> getYTransactionsByGroupId(String groupId){
        return redisService.listByGroupId(groupId);
    }

}
