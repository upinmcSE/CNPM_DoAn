package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Integer> {
    Optional<OrderType> findByName(String name);
}
