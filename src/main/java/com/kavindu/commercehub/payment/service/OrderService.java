package com.kavindu.commercehub.payment.service;

import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Authentication.repositories.UserRepository;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.payment.Repository.OrderItemRepository;
import com.kavindu.commercehub.payment.Repository.OrderRepository;
import com.kavindu.commercehub.payment.Repository.ShippingDeatilsRepository;
import com.kavindu.commercehub.payment.dto.OrderItemRequest;
import com.kavindu.commercehub.payment.dto.OrderRequest;
import com.kavindu.commercehub.payment.models.Order_items;
import com.kavindu.commercehub.payment.models.Orders;
import com.kavindu.commercehub.payment.models.ShippingDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShippingDeatilsRepository shippingDeatilsRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ShippingDeatilsRepository shippingDeatilsRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shippingDeatilsRepository = shippingDeatilsRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Orders placeOrder(OrderRequest request, String username) {

        AppUser user=userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));

        Orders order=new Orders();
        order.setDate(new Date());
        order.setStatus("PENDING");

        List<Order_items> orderItemsList=new ArrayList<>();
        int totalAmount=0;

        for(OrderItemRequest itemRequest:request.getItems()){
            Product product=productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(()->new RuntimeException("Product Not found"));

            Order_items item=new Order_items();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(product.getPrice() * itemRequest.getQuantity());
            item.setOrder(order);

            orderItemsList.add(item);
            totalAmount += item.getPrice();
        }

        order.setTotal_amount(totalAmount);
        order.setUser(user);
        order.setOrderItemsList(orderItemsList);

        ShippingDetails shipping=new ShippingDetails();
        shipping.setShippingAddress(request.getShippingAddress());
        shipping.setPhoneNo(request.getPhoneNo());
        shipping.setOrder(order);
        shipping.setAppUser(user);
        order.setShippingDetails(shipping);

        return orderRepository.save(order);
    }
}
