package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.RoleRequest;
import com.upinmcSE.coffeeshop.dto.response.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleService {
    RoleResponse add(RoleRequest request);
    RoleResponse update(String id, RoleRequest request);
    List<RoleResponse> getAll();
    void delete(String id);
}
