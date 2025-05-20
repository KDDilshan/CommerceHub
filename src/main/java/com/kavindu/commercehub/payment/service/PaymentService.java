package com.kavindu.commercehub.payment.service;

import com.kavindu.commercehub.payment.Repository.OrderRepository;
import com.kavindu.commercehub.payment.Repository.PaymentRepository;
import com.kavindu.commercehub.payment.dto.PaymentRequest;
import com.kavindu.commercehub.payment.models.Orders;
import com.kavindu.commercehub.payment.models.Payment;
import com.kavindu.commercehub.payment.models.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Payment processPayment(PaymentRequest request) {
        Orders orders=orderRepository.findById(request.getOrderId())
                .orElseThrow(()->new RuntimeException("Order not found"));

        Payment payment=new Payment();
        payment.setId(UUID.randomUUID());
        payment.setOrders(orders);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(request.getPaymentStatus());
        payment.setTransactionId(request.getTransactionId());

        Payment savedpayment=paymentRepository.save(payment);

        if(request.getPaymentStatus()== PaymentStatus.SUCCESS){
            orders.setStatus("PAID");
            orderRepository.save(orders);
        }

        return savedpayment;
    }
}
