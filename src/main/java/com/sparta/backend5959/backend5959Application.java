package com.sparta.backend5959;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class backend5959Application {
    public static void main(String[] args) {
        SpringApplication.run(backend5959Application.class, args);
    }

}
