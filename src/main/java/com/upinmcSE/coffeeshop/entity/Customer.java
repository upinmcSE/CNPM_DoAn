package com.upinmcSE.coffeeshop.entity;

import com.upinmcSE.coffeeshop.enums.MenberLV;
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
public class Customer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    MenberLV menberLV;
    Integer point;
}
