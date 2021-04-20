package com.whut.yt.test.server2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.whut.yt")
public class YTTestServer2Application {

    public static void main(String[] args) {
        SpringApplication.run(YTTestServer2Application.class, args);
    }

}
