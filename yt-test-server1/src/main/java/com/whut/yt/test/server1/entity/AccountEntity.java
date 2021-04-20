package com.whut.yt.test.server1.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 16:28
 * @Description:
 */
@Data
@TableName("account")
public class AccountEntity {

    @TableId
    private Long id;

    private float money;

}
