package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Payment;
import com.upinmcSE.coffeeshop.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findAllByStatusAndCreatedTimeBefore(Status status, LocalDateTime createdTime);

    Optional<Payment> findByOrderId(String orderId);
}
