package com.whut.yt.test.server2.service.impl;


import com.whut.yt.core.annotation.YTransaction;
import com.whut.yt.test.server2.dao.AccountDao;
import com.whut.yt.test.server2.entity.AccountEntity;
import com.whut.yt.test.server2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 16:42
 * @Description:
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;

    @YTransaction
    @Override
    public void accountAdd(String money) {
        float v = Float.parseFloat(money);
        accountDao.accountAdd(v);
//        int i = 10/0;
    }

    @Override
    public AccountEntity query() {
        return accountDao.selectById(1L);
    }
}
