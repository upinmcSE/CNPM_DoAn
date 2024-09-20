package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Integer> {
    Optional<WorkTime> findByName(String name);
}
