package com.kavindu.commercehub.payment.controller;

import com.kavindu.commercehub.payment.dto.PaymentRequest;
import com.kavindu.commercehub.payment.models.Payment;
import com.kavindu.commercehub.payment.service.OrderService;
import com.kavindu.commercehub.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> makePayment(@RequestBody PaymentRequest request){
        Payment payment=paymentService.processPayment(request);
        return ResponseEntity.ok(payment);
    }
}
