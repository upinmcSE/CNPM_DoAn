package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.EmployeeRequest;
import com.upinmcSE.coffeeshop.dto.response.EmployeeResponse;
import com.upinmcSE.coffeeshop.entity.Employee;
import com.upinmcSE.coffeeshop.mapper.EmployeeMapper;
import com.upinmcSE.coffeeshop.repository.EmployeeLVRepository;
import com.upinmcSE.coffeeshop.repository.EmployeeRepository;
import com.upinmcSE.coffeeshop.repository.RoleRepository;
import com.upinmcSE.coffeeshop.repository.WorkTimeRepository;
import com.upinmcSE.coffeeshop.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    WorkTimeRepository workTimeRepository;
    RoleRepository roleRepository;
    EmployeeLVRepository employeeLVRepository;
    EmployeeMapper employeeMapper;
    PasswordEncoder passwordEncoder;
    @Override
    public EmployeeResponse add(EmployeeRequest request) {
        if(!(request.password().equals(request.rePassword())))
            throw new RuntimeException("password not match");

        Employee employee = employeeMapper.toEmployee(request);


        return null;
    }

    @Override
    public EmployeeResponse update(String id, EmployeeRequest request) {
        return null;
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
