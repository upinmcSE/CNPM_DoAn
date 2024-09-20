package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.EmployeeLv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeLVRepository extends JpaRepository<EmployeeLv, Integer> {
    Optional<EmployeeLv> findByName(String name);
}
