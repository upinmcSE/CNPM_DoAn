package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.request.RoleRequest;
import com.upinmcSE.coffeeshop.dto.response.RoleResponse;
import com.upinmcSE.coffeeshop.entity.Role;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.mapper.RoleMapper;
import com.upinmcSE.coffeeshop.repository.PermissionRepository;
import com.upinmcSE.coffeeshop.repository.RoleRepository;
import com.upinmcSE.coffeeshop.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse add(RoleRequest request) {
        if(roleRepository.existsByName(request.name()))
            throw new ErrorException(ErrorCode.ROLE_EXISTED);

        Role role = roleMapper.toRole(request);

        var permission = permissionRepository.findAllByName(request.permissions());

        role.setPermissions(new HashSet<>(permission));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public RoleResponse update(String id, RoleRequest request) {
        return null;
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {

    }
}
