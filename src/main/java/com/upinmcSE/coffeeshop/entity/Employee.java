package com.upinmcSE.coffeeshop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "employee_lv_id")
    EmployeeLv employeeLv;

    double salary;

    @ManyToOne
    @JoinColumn(name = "work_time_id")
    WorkTime workTime;
}
