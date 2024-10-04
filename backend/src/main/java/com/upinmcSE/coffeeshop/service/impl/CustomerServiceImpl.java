package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.CustomerRequest;
import com.upinmcSE.coffeeshop.dto.response.CustomerResponse;
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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    @Override
    public CustomerResponse update(String id, CustomerRequest request) {
        return null;
    }

    @Override
    public List<CustomerResponse> getAll() {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
