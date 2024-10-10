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

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    double salary;
    Integer workingDays;

    @ManyToOne
    @JoinColumn(name = "work_time_id")
    WorkTime workTime;
}
