package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.dto.request.RoleRequest;
import com.upinmcSE.coffeeshop.dto.response.RoleResponse;
import com.upinmcSE.coffeeshop.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public Role toRole(RoleRequest request){
        return Role.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public RoleResponse toRoleResponse(Role role){
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions())
                .build();
    }
}
