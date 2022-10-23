package com.sparta.backend5959;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Backend5959Application {
    public static void main(String[] args) {
        SpringApplication.run(Backend5959Application.class, args);
    }

}
