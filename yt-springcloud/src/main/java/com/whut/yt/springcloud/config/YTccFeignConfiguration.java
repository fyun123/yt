package com.whut.yt.springcloud.config;

import com.whut.yt.springcloud.feign.YTccFeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 一大岐
 * @Date: 2021/4/15 14:59
 * @Description: feign配置类
 */
@Configuration
public class YTccFeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new YTccFeignInterceptor();
    }

}
