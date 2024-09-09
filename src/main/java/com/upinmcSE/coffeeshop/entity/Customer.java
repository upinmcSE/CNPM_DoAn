package com.upinmcSE.coffeeshop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "member_lv_id")
    MemberLv memberLv;

    Integer point;

}
