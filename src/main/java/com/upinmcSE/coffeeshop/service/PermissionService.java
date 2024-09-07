package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.request.PermissionRequest;
import com.upinmcSE.coffeeshop.dto.response.PermissionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PermissionService {
    PermissionResponse add(PermissionRequest request);
    PermissionResponse update(String id, PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String id);
}
