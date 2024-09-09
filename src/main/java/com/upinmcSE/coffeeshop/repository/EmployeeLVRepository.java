package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.EmployeeLv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EmployeeLVRepository extends JpaRepository<EmployeeLv, Integer> {
}
