package com.upinmcSE.coffeeshop.entity;

import com.upinmcSE.coffeeshop.enums.EmployeeLV;
import com.upinmcSE.coffeeshop.enums.WorkTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    EmployeeLV employeeLV;
    double salary;
    WorkTime workTime;
}
