package com.kavindu.commercehub.payment.Repository;

import com.kavindu.commercehub.payment.models.Order_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<Order_items,Integer> {
}
