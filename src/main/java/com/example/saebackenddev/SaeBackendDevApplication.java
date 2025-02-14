package com.example.saebackenddev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SaeBackendDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaeBackendDevApplication.class, args);
    }

}
