package com.upinmcSE.coffeeshop.dto.request;

public record EmployeeRequest(
        String username,
        String password,
        String rePassword,
        String fullName,
        Integer age,
        boolean gender,
        String role,
        String employeeLv,
        double salary,
        String workTime
) {
}
