package com.jili20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Random;

/**
 * @author bing_  @create 2022/1/6-18:25
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    /**
     * 生成随机数
     * @return
     */
    @Bean
    public Random createRandom(){
        return new Random();
    }
}

