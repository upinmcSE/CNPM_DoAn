package com.upinmcSE.coffeeshop.entity;

import com.upinmcSE.coffeeshop.enums.PaymentType;
import com.upinmcSE.coffeeshop.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;
    String phoneNumber;
    String address;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToOne
    @JoinColumn(name = "order_id" ,nullable = false)
    Order order;

    @CreatedDate
    LocalDateTime createdTime;
}

