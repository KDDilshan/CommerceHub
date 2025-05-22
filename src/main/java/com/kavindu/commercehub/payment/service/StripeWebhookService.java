package com.kavindu.commercehub.payment.service;

import com.kavindu.commercehub.payment.Repository.OrderRepository;
import com.kavindu.commercehub.payment.dto.PaymentRequest;
import com.kavindu.commercehub.payment.models.enums.PaymentStatus;
import com.kavindu.commercehub.payment.models.enums.PaymentType;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.ApiResource;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

@Service
public class StripeWebhookService {
    private final StripePaymentService paymentService;
    private final OrderRepository ordersRepository;

    public StripeWebhookService(StripePaymentService paymentService, OrderRepository ordersRepository) {
        this.paymentService = paymentService;
        this.ordersRepository = ordersRepository;
    }

    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) {
        String payload = "";

        try (Scanner s = new Scanner(request.getInputStream(), "UTF-8")) {
            payload = s.useDelimiter("\\A").hasNext() ? s.next() : "";
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("");
        }

        Event event;
        try {
            event = ApiResource.GSON.fromJson(payload, Event.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("");
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if (intent != null) {
                String stripeTransactionId = intent.getId();
                String orderIdStr = intent.getMetadata().get("orderId");

                if (orderIdStr != null) {
                    UUID orderId = UUID.fromString(orderIdStr);

                    PaymentRequest req = new PaymentRequest();
                    req.setOrderId(orderId);
                    req.setPaymentMethod(PaymentType.STRIPE);
                    req.setPaymentStatus(PaymentStatus.SUCCESS);
                    req.setTransactionId(stripeTransactionId);

                    paymentService.processPayment(req);
                }
            }
        }

        return ResponseEntity.ok("Webhook processed");
    }
}
