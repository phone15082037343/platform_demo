package com.platform.client.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientUserApplication.class, args);
    }

}
