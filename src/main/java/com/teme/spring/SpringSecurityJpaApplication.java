package com.teme.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SpringSecurityJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJpaApplication.class, args);
    }

}

