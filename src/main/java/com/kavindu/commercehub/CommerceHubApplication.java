package com.kavindu.commercehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CommerceHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceHubApplication.class, args);
    }

}
