package com.mbueno.pointcontrol.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) //ignore basic auth
public class PointControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(PointControlApplication.class, args);
    }
}
