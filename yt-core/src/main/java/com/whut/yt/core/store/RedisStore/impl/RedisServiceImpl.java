package com.whut.yt.core.store.RedisStore.impl;

import com.alibaba.fastjson.JSON;
import com.whut.yt.common.exception.YTccException;
import com.whut.yt.core.entity.YTransactionEntity;
import com.whut.yt.core.store.RedisStore.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 一大岐
 * @Date: 2021/4/20 8:13
 * @Description: redis业务实现类
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 保存事务到redis
     *
     * @param yTransactionEntity 事务
     */
    @Override
    public void save(final YTransactionEntity yTransactionEntity) {
        try{
            BoundHashOperations<String, String, String> groupHash = getGroupHash(yTransactionEntity.getGroupId());
            groupHash.put(yTransactionEntity.getYTransactionId(),JSON.toJSONString(yTransactionEntity));
        } catch (Exception e){
            throw new  YTccException(e);
        }
    }

    /**
     * 根据事务组id查询所有事务
     *
     * @param groupId 事务组id
     * @return 事务
     */
    @Override
    public List<YTransactionEntity> listByGroupId(final String groupId) {
        BoundHashOperations<String, String, String> groupHash = getGroupHash(groupId);
        List<String> values = groupHash.values();
        if (values != null && values.size() > 0){
            return values.stream().map(item -> JSON.parseObject(item, YTransactionEntity.class)).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 从redis中移除事务
     *
     * @param yTransactionEntity 事务
     */
    @Override
    public void remove(final YTransactionEntity yTransactionEntity) {
        try{
            BoundHashOperations<String, String, String> groupHash = getGroupHash(yTransactionEntity.getGroupId());
            groupHash.delete(yTransactionEntity.getYTransactionId());
        }catch (Exception e){
            throw new  YTccException(e);
        }
    }

    /**
     * 哈希操作（groupId:transactionId->transaction）
     * @param groupId 事务组id
     * @return BoundHashOperations
     */
    public BoundHashOperations<String, String, String> getGroupHash(String groupId){
        return redisTemplate.boundHashOps(groupId);
    }
}
