package com.upinmcSE.coffeeshop.dto.request;

public record EmployeeRequest(
        String username,
        String password,
        String rePassword,
        String fullName,
        String email,
        Integer age,
        boolean gender,
        String employeeLv,
        double salary,
        String workTime
) {
}
