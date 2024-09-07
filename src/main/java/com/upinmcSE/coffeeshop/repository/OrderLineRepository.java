package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, String> {
}
