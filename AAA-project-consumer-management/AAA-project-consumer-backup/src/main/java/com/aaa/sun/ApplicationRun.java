package com.aaa.sun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/15 15:01
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients( basePackages =  {"com.aaa.sun"})
public class ApplicationRun {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class,args);
    }
}
