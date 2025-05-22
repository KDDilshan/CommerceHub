package com.kavindu.commercehub.Stripe.Controller;

import com.kavindu.commercehub.Stripe.service.StripePaymentService;
import com.kavindu.commercehub.Stripe.service.StripeWebhookService;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/Payment")
public class StripeController {

    private final StripePaymentService stripePaymentService;
    private final StripeWebhookService stripeWebhookService;


    public StripeController(StripePaymentService stripePaymentService, StripeWebhookService stripeWebhookService) {
        this.stripePaymentService = stripePaymentService;
        this.stripeWebhookService = stripeWebhookService;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String,String>> createPaymentIntent(@RequestBody Map<String,Object> data) throws StripeException{
        Map<String ,String> response=stripePaymentService.createPaymentIntent(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(HttpServletRequest request){
        return stripeWebhookService.handleStripeWebhook(request);
    }
}
