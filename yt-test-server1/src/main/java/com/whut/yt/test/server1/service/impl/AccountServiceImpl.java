package com.whut.yt.test.server1.service.impl;

import com.whut.yt.core.annotation.YTransaction;
import com.whut.yt.test.server1.entity.AccountEntity;
import com.whut.yt.test.server1.feign.Server2FeignService;
import com.whut.yt.test.server1.service.AccountService;
import com.whut.yt.test.server1.vo.Account;
import com.whut.yt.test.server1.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whut.yt.test.server1.dao.AccountDao;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 16:42
 * @Description:
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;

    @Autowired
    Server2FeignService server2FeignService;

    @Override
    public void accountSub(float money) {
        accountDao.accountSub(money);
    }

    @Override
    public Account query() {
        AccountEntity account1 = accountDao.selectById(1L);
        AccountVo account2 = server2FeignService.queryAccount2();
        Account account = new Account();
        account.setAccount1(account1.getMoney());
        account.setAccount2(account2.getMoney());
        return account;
    }

    @YTransaction
    @Override
    public void transfer(String money) {
        float v = Float.parseFloat(money);
        this.accountSub(v);
        server2FeignService.accountAdd(money);
        int i = 10/0;
    }
}
