package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
