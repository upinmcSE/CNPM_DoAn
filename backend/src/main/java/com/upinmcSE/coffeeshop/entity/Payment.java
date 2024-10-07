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
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String paymentType;

    @OneToOne
    @JoinColumn(name = "order_id" ,nullable = false)
    Order order;
}
