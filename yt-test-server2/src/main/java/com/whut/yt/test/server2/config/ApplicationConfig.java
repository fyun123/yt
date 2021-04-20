package com.whut.yt.test.server2.config;

import com.whut.yt.springcloud.aspect.SpringCloudYTransactionAspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: 一大岐
 * @Date: 2021/4/16 21:24
 * @Description:
 */
@Configuration
@Import({
        SpringCloudYTransactionAspect.class// 这里写上注解的切面类
})
public class ApplicationConfig {
}
