package com.upinmcSE.coffeeshop.dto.response;

import com.upinmcSE.coffeeshop.entity.MemberLv;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record CustomerResponse(
        String id,
        String username,
        String fullName,
        String email,
        Integer age,
        boolean gender,
        String menberLV,
        Integer point,
        LocalDate createdDate,
        LocalDate modifiedDate

) {
}
