package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.EmployeeRequest;
import com.upinmcSE.coffeeshop.dto.request.EmployeeUpdateRequest;
import com.upinmcSE.coffeeshop.dto.response.EmployeeResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
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
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    WorkTimeRepository workTimeRepository;
    RoleRepository roleRepository;
    EmployeeLVRepository employeeLVRepository;
    EmployeeMapper employeeMapper;
    PasswordEncoder passwordEncoder;

    @Transactional
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

    @Transactional
    @Override
    public EmployeeResponse update(String id, EmployeeUpdateRequest request) {
        var employee = employeeRepository.findById(id).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_EMPLOYEE));
        employee.setFullName(request.fullName());
        EmployeeLv employeeLv = employeeLVRepository.findByName(request.employeeLv()).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_EMPLOYEELV));
        employee.setEmployeeLv(employeeLv);
        employee.setSalary(request.salary());
        WorkTime workTime = workTimeRepository.findByName(request.workTime()).orElseThrow(
                () -> new ErrorException(ErrorCode.WORK_TIME));
        employee.setWorkTime(workTime);
        return employeeMapper.toEmployeeResponse(employeeRepository.saveAndFlush(employee));
    }

    @Transactional
    @Override
    public PageResponse<EmployeeResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        var pageData = employeeRepository.findAll(pageable);
        log.info("Page data: {}", pageData);

        return PageResponse.<EmployeeResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(employeeMapper::toEmployeeResponse).toList())
                .build();
    }

    @Override
    public void checkin(String employeeId) {
        var employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_EMPLOYEE));
        employee.setWorkingDays(employee.getWorkingDays() + 1);
        employeeRepository.saveAndFlush(employee);
    }

    @Transactional
    @Override
    public void delete(String id) {
        employeeRepository.deleteById(id);
    }
}
