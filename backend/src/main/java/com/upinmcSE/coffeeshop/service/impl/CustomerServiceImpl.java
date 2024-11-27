package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.CustomerRequest;
import com.upinmcSE.coffeeshop.dto.request.CustomerUpdateRequest;
import com.upinmcSE.coffeeshop.dto.response.CustomerResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.ProductResponse;
import com.upinmcSE.coffeeshop.entity.Customer;
import com.upinmcSE.coffeeshop.entity.MemberLv;
import com.upinmcSE.coffeeshop.enums.MemberLV;
import com.upinmcSE.coffeeshop.enums.RoleType;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.mapper.CustomerMapper;
import com.upinmcSE.coffeeshop.repository.CustomerRepository;
import com.upinmcSE.coffeeshop.repository.MemberLVRepository;
import com.upinmcSE.coffeeshop.repository.RoleRepository;
import com.upinmcSE.coffeeshop.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;
    RoleRepository roleRepository;
    MemberLVRepository memberLVRepository;
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public CustomerResponse add(CustomerRequest request) {
        if(customerRepository.existsByUsername(request.username()))
            throw new ErrorException(ErrorCode.USER_EXISTED);

        if(!(request.password().equals(request.rePassword())))
            throw new ErrorException(ErrorCode.NOT_MATCH_PW);

        var customer = customerMapper.toCustomer(request);

        customer.setPassword(passwordEncoder.encode(request.password()));

        var role = roleRepository.findByName(String.valueOf(RoleType.CUSTOMER)).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_ROLE));
        customer.setRole(role);

        MemberLv memberLv = memberLVRepository.findByName("MEMBER");
        customer.setMemberLv(memberLv);
        customer.setPoint(0);

        customer = customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    @Transactional
    @Override
    public CustomerResponse update(CustomerUpdateRequest request, Customer customer) {
        customer.setFullName(request.fullName());
        customer.setGender(request.gender());
        customer.setAge(request.age());
        customer.setEmail(request.email());
        customer = customerRepository.saveAndFlush(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    @Transactional
    @Override
    public PageResponse<CustomerResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Customer> pageData = customerRepository.findAll(pageable);

        return PageResponse.<CustomerResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(customerMapper::toCustomerResponse).toList())
                .build();
    }

    @Transactional
    @Override
    public CustomerResponse getById() {
        var context = SecurityContextHolder.getContext();
        var username = context.getAuthentication().getName();
        var customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public long countCustomerThisMonth() {
        return customerRepository.countCustomersCreatedThisMonth();
    }

    @Transactional
    @Override
    public void delete(String id) {
        var customer = customerRepository.findById(id).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_CUSTOMER));
        customerRepository.delete(customer);
    }
}
