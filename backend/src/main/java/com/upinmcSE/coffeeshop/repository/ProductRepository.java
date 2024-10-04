package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    boolean existsByName(String s);

    Page<Product> findAllByCategory_Name(String category, Pageable pageable);
}
