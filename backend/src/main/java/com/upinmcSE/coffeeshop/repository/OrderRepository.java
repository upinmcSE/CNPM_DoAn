package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findAllByCustomerId(String customerId, Pageable pageable);

    @Query("SELECT o.id AS order_id, o.customer.id AS customer_id, o.totalPrice AS total_price, ol.id AS order_line_id, ol.product.id AS product_id, ol.amount " +
            "FROM Order o " +
            "JOIN o.orderLines ol")
    List<Object[]> findOrderAndOrderLines();

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE MONTH(o.createdDate) = MONTH(CURRENT_DATE) AND YEAR(o.createdDate) = YEAR(CURRENT_DATE)")
    Double calculateTotalRevenueForCurrentMonth();
}
