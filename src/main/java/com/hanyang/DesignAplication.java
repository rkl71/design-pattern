package com.hanyang;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories
@SpringBootApplication
@EnableApolloConfig
public class DesignAplication {
    public static void main(String[] args) {
        SpringApplication.run(DesignAplication.class, args);
    }
}
