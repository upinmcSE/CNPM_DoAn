package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findAllByCustomerId(String customerId, Pageable pageable);
}
