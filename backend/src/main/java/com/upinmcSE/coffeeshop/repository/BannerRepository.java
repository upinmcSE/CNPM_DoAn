package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {
}
