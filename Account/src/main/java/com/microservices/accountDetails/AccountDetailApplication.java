package com.microservices.accountDetails;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AccountDetailApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountDetailApplication.class, args);
    }
}
