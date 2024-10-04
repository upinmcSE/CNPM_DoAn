package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByUsername(String username);

    Optional<Customer> findByUsername(String username);
}
