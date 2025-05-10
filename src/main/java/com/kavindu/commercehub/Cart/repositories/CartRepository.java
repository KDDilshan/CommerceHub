package com.kavindu.commercehub.Cart.repositories;

import com.kavindu.commercehub.Cart.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCustomerId(UUID customerId);
}
