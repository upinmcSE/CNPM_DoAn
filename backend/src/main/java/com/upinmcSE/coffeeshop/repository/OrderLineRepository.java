package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.OrderLine;
import com.upinmcSE.coffeeshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, String> {
    @Query("SELECT ol.product " +
            "FROM OrderLine ol " +
            "GROUP BY ol.product " +
            "ORDER BY SUM(ol.amount) DESC")
    Page<Product> findTopProductsByTotalAmount(Pageable pageable);
}
