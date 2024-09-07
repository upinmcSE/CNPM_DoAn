package com.upinmcSE.coffeeshop.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    String username;
    String password;
    String fullName;
    Integer age;
    boolean gender;
    LocalDate createdDate;
    LocalDate modifiedDate;

    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

}
