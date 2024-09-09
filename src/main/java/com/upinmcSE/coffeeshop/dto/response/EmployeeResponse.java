package com.upinmcSE.coffeeshop.dto.response;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record EmployeeResponse(
        String id,
        String username,
        String fullName,
        Integer age,
        boolean gender,
        String role,
        String employeeLv,
        double salary,
        String workTime,
        LocalDate createdDate,
        LocalDate modifiedDate

) {
}
