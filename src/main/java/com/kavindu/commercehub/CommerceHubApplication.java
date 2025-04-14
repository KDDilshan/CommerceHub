package com.kavindu.commercehub;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CommerceHubApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        System.setProperty("DB_URL", dotenv.get("DB_URL_TEST"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME_TEST"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD_TEST"));

        SpringApplication.run(CommerceHubApplication.class, args);
    }

}
