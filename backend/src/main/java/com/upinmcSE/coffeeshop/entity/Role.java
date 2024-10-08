package com.upinmcSE.coffeeshop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String description;

    @OneToMany(mappedBy = "role")
    List<Customer> customers;

    @OneToMany(mappedBy = "role")
    List<Employee> employees;

    @ManyToMany
    Set<Permission> permissions;
}
