package com.whut.yt.test.server1.service;


import com.whut.yt.test.server1.vo.Account;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 16:40
 * @Description:
 */
public interface AccountService {
    void accountSub(float money);

    Account query();

    void transfer(String money);
}
