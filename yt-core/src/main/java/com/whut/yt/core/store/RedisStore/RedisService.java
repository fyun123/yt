package com.whut.yt.core.store.RedisStore;

import com.whut.yt.core.entity.YTransactionEntity;

import java.util.List;

/**
 * @Author: 一大岐
 * @Date: 2021/4/19 22:22
 * @Description: redis业务接口
 */
public interface RedisService {

    /**
     * 保存事务到redis
     *
     * @param yTransactionEntity 事务
     */
    void save(YTransactionEntity yTransactionEntity);

    /**
     * 根据事务组id查询所有事务
     *
     * @param groupId 事务组id
     * @return 事务
     */
    List<YTransactionEntity> listByGroupId(String groupId);

    /**
     * 从redis中移除事务
     *
     * @param yTransactionEntity 事务
     */
    void remove(YTransactionEntity yTransactionEntity);

}
