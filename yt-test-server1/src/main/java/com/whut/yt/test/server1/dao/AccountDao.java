package com.whut.yt.test.server1.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.yt.test.server1.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 17:00
 * @Description:
 */
@Mapper
public interface AccountDao extends BaseMapper<AccountEntity> {
    void accountSub(float money);
}
