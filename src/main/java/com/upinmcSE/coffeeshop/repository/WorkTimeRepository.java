package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Integer> {
    WorkTime findByName(String name);
}
