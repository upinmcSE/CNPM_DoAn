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
public class OrderType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;

    @OneToMany(mappedBy = "orderType",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orderList;

}
