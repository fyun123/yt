package com.whut.yt.test.server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.whut.yt")
public class YTTestServer1Application {

    public static void main(String[] args) {
        SpringApplication.run(YTTestServer1Application.class, args);
    }

}
