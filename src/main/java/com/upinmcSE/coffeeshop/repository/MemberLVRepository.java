package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.MemberLv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberLVRepository extends JpaRepository<MemberLv, Integer> {
    MemberLv findByName(String name);
}
