package com.kavindu.commercehub.payment.controller;

import com.kavindu.commercehub.payment.dto.OrderRequest;
import com.kavindu.commercehub.payment.models.Orders;
import com.kavindu.commercehub.payment.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Orders> PlaceOrder(@RequestBody OrderRequest request, Principal principal){
        Orders orders=orderService.placeOrder(request,principal.getName());
        return ResponseEntity.ok(orders);
    }
}
