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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;

    double price;
    String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category;


}
