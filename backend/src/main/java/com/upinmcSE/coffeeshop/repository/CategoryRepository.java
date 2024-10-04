package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    boolean existsByName(String name);

    Optional<Category> findByName(String categoryName);
}
