package com.kavindu.commercehub.payment.models;

import com.kavindu.commercehub.Product.models.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order_items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    private Orders order;

    @OneToMany
    private Product product;

    private int quantity;

    private int price;
}
