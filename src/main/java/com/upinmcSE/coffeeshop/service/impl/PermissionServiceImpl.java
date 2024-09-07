package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.PermissionRequest;
import com.upinmcSE.coffeeshop.dto.response.PermissionResponse;
import com.upinmcSE.coffeeshop.entity.Permission;
import com.upinmcSE.coffeeshop.mapper.PermissionMapper;
import com.upinmcSE.coffeeshop.repository.PermissionRepository;
import com.upinmcSE.coffeeshop.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    @Override
    public PermissionResponse add(PermissionRequest request) {
        var permission = permissionMapper.toPermission(request);

        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public PermissionResponse update(String id, PermissionRequest request) {
        Permission permission = permissionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("not found"));

        permission.setName(request.name());
        permission.setDescription(request.description());
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toPermissionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        permissionRepository.deleteById(id);
    }
}
