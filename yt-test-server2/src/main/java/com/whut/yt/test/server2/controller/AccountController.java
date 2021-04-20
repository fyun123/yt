package com.whut.yt.test.server2.controller;

import com.whut.yt.test.server2.entity.AccountEntity;
import com.whut.yt.test.server2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 16:38
 * @Description:
 */
@Controller
public class AccountController {

    @Autowired
    AccountService accountService;


    @ResponseBody
    @GetMapping("/account/{money}")
    public String accountAdd(@PathVariable("money") String money){
        accountService.accountAdd(money);
        return "Ok";
    }

    @ResponseBody
    @GetMapping("/query/account")
    public AccountEntity queryAccount(){
        AccountEntity accountEntity = accountService.query();
        return accountEntity;
    }

}
