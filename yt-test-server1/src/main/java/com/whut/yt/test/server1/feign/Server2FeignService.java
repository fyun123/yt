package com.whut.yt.test.server1.feign;

import com.whut.yt.test.server1.vo.AccountVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 19:59
 * @Description:
 */
@FeignClient("ytcc-server2")
public interface Server2FeignService {

//    @YTransaction
    @GetMapping("/account/{money}")
    String accountAdd(@PathVariable("money") String money);

    @GetMapping("/query/account")
    AccountVo queryAccount2();

}
