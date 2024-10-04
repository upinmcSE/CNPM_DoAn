package com.upinmcSE.coffeeshop.repository;

import com.upinmcSE.coffeeshop.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

    boolean existsByName(String name);

    @Query("SELECT p FROM Permission p WHERE p.name IN :permissions")
    List<Permission> findAllByName(@Param("permissions") Collection<String> permissions);
}
