package com.kavindu.commercehub.payment.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class stripeConfig {

    @Value("${stripe.secret.key}")
    private String SecretKey;

    @PostConstruct
    public void Setup(){
        Stripe.apiKey=SecretKey;
    }
}
