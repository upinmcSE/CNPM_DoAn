package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.EmployeeRequest;
import com.upinmcSE.coffeeshop.dto.response.EmployeeResponse;
import com.upinmcSE.coffeeshop.entity.Employee;
import com.upinmcSE.coffeeshop.entity.EmployeeLv;
import com.upinmcSE.coffeeshop.entity.Role;
import com.upinmcSE.coffeeshop.entity.WorkTime;
import com.upinmcSE.coffeeshop.enums.RoleType;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
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
        if(employeeRepository.existsByUsername(request.username()))
            throw new ErrorException(ErrorCode.USER_EXISTED);

        if(!(request.password().equals(request.rePassword())))
            throw new ErrorException(ErrorCode.NOT_MATCH_PW);

        Employee employee = employeeMapper.toEmployee(request);
        employee.setPassword(passwordEncoder.encode(request.password()));

        Role role = roleRepository.findByName(String.valueOf(RoleType.EMPLOYEE)).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_ROLE));
        employee.setRole(role);

        EmployeeLv employeeLv = employeeLVRepository.findByName(request.employeeLv()).orElseThrow(
                () -> new ErrorException(ErrorCode.EMPLOY_LV));
        employee.setEmployeeLv(employeeLv);

        WorkTime workTime = workTimeRepository.findByName(request.workTime()).orElseThrow(
                () -> new ErrorException(ErrorCode.WORK_TIME));
        employee.setWorkTime(workTime);

        employee.setWorkingDays(0);

        employee = employeeRepository.save(employee);

        return employeeMapper.toEmployeeResponse(employee);
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
