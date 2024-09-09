package com.upinmcSE.coffeeshop.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Setter
@Getter
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class User {
    String username;
    String password;
    String fullName;
    Integer age;
    boolean gender;
    @CreatedDate
    LocalDate createdDate;

    @LastModifiedDate
    LocalDate modifiedDate;

    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

}
