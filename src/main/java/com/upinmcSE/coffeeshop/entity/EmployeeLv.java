package com.upinmcSE.coffeeshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeLv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;

    @OneToMany(mappedBy = "employeeLv")
    List<Employee> employees;

}
