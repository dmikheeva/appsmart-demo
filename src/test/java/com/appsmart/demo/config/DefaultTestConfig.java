package com.appsmart.demo.config;

import com.appsmart.demo.security.config.WebSecurityConfiguration;
import com.appsmart.demo.service.impl.CustomerService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@EnableAutoConfiguration(exclude = { WebSecurityConfiguration.class})
public class DefaultTestConfig {

    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }

}