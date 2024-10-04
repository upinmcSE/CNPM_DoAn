package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByUsername(String username);

    Optional<Employee> findByUsername(String username);
}
