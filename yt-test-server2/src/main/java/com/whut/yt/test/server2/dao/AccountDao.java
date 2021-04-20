package com.whut.yt.test.server2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whut.yt.test.server2.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 16:31
 * @Description:
 */
@Mapper
public interface AccountDao extends BaseMapper<AccountEntity> {
    void accountAdd(@Param("money") float money);
}
