package com.upinmcSE.coffeeshop.dto.request;

import lombok.Builder;

@Builder
public record EmployeeUpdateRequest(
        String fullName,
        String employeeLv,
        double salary,
        String workTime
) {
}
