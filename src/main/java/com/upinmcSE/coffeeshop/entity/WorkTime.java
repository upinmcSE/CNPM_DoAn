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
public class WorkTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String description;

    @OneToMany(mappedBy = "workTime")
    List<Employee> employees;
}
