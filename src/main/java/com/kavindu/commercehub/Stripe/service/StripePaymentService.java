package com.kavindu.commercehub.Stripe.service;

import com.kavindu.commercehub.payment.Repository.OrderRepository;
import com.kavindu.commercehub.payment.Repository.PaymentRepository;
import com.kavindu.commercehub.payment.dto.PaymentRequest;
import com.kavindu.commercehub.payment.models.Orders;
import com.kavindu.commercehub.payment.models.Payment;
import com.kavindu.commercehub.payment.service.EmailService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class StripePaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final EmailService emailService;

    public StripePaymentService(OrderRepository orderRepository, PaymentRepository paymentRepository, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.emailService = emailService;
    }

    public Map<String, String> createPaymentIntent(Map<String, Object> data) throws StripeException {
        int amount = (int) data.get("amount");
        String orderId = data.get("orderId").toString();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) amount)
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .putMetadata("orderId", orderId)
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", intent.getClientSecret());
        return response;
    }

    public void processPayment(PaymentRequest request) {
        Orders order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setOrders(order);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(request.getPaymentStatus());
        payment.setTransactionId(request.getTransactionId());

        paymentRepository.save(payment);

        order.setStatus("PAID");
        orderRepository.save(order);

        emailService.sendConfirmation(
                order.getUser().getEmail(),
                "Order Confirmed",
                "Thank you for your order. Your payment was successful"
        );
    }

}
