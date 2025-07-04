package com.kavindu.commercehub.payment.Repository;

import com.kavindu.commercehub.payment.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {
}
