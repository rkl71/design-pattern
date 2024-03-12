package com.hanyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DesignAplication {
    public static void main(String[] args) {
        SpringApplication.run(DesignAplication.class, args);
    }
}
