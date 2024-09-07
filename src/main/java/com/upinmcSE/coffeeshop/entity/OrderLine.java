package com.upinmcSE.coffeeshop.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    Integer amount;


    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;
}
