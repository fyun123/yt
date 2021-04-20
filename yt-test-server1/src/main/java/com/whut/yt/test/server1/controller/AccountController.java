package com.whut.yt.test.server1.controller;


import com.whut.yt.test.server1.feign.Server2FeignService;
import com.whut.yt.test.server1.service.AccountService;
import com.whut.yt.test.server1.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 16:38
 * @Description:
 */
@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    Server2FeignService server2FeignService;

    @GetMapping("/")
    public String index(Model model){
        Account account = accountService.query();
        model.addAttribute("account",account);
        return "index";
    }

    @GetMapping("/account/{money}")
    public String accountSub(@PathVariable("money") String money){
        accountService.transfer(money);
        return "redirect:http://localhost:9200/";
    }

}
