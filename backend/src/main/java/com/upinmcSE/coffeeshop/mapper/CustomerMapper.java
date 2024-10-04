package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.dto.request.CustomerRequest;
import com.upinmcSE.coffeeshop.dto.response.CustomerResponse;
import com.upinmcSE.coffeeshop.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request){
        return Customer.builder()
                .username(request.username())
                .fullName(request.fullName())
                .email(request.email())
                .age(request.age())
                .gender(request.gender())
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .username(customer.getUsername())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .age(customer.getAge())
                .gender(customer.isGender())
                .menberLV(customer.getMemberLv().getName())
                .point(customer.getPoint())
                .createdDate(customer.getCreatedDate())
                .modifiedDate(customer.getModifiedDate())
                .build();
    }


}
