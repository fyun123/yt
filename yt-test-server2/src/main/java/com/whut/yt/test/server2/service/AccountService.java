package com.whut.yt.test.server2.service;

import com.whut.yt.test.server2.entity.AccountEntity;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 16:40
 * @Description:
 */
public interface AccountService {
    void accountAdd(String money);

    AccountEntity query();
}
