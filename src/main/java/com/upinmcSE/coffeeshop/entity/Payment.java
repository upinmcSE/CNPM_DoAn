package com.upinmcSE.coffeeshop.entity;

import com.upinmcSE.coffeeshop.enums.PaymentType;
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

    PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @OneToOne
    @JoinColumn(name = "order_id" ,nullable = false)
    Order order;}
