package com.kavindu.commercehub;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class Configuration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
