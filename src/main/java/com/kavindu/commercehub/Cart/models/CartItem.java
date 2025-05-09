package com.kavindu.commercehub.Cart.models;

import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Product.models.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {
    @Id
    @GeneratedValue
    private int id;

    private int quantity;

    @ManyToOne
    private AppUser Customer;

    @ManyToOne
    private Product product;
}
