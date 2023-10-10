package com.rang.selfstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SelfstarterGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfstarterGatewayApplication.class, args);
    }

}
