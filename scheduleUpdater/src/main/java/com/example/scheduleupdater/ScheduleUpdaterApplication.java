package com.example.scheduleupdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@Configuration(proxyBeanMethods = false)
public class ScheduleUpdaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleUpdaterApplication.class, args);
    }

}
