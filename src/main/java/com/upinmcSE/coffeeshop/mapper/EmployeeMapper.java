package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.dto.request.EmployeeRequest;
import com.upinmcSE.coffeeshop.dto.response.EmployeeResponse;
import com.upinmcSE.coffeeshop.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee toEmployee(EmployeeRequest request){
        return Employee.builder()
                .username(request.username())
                .fullName(request.fullName())
                .age(request.age())
                .gender(request.gender())
                .salary(request.salary())
                .build();
    }

    public EmployeeResponse toEmployeeResponse(Employee employee){
        return EmployeeResponse.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .fullName(employee.getFullName())
                .age(employee.getAge())
                .gender(employee.isGender())
                .role(employee.getRole().getName())
                .employeeLv(employee.getEmployeeLv().getName())
                .salary(employee.getSalary())
                .workTime(employee.getWorkTime().getName())
                .createdDate(employee.getCreatedDate())
                .modifiedDate(employee.getModifiedDate())
                .build();
    }
}
