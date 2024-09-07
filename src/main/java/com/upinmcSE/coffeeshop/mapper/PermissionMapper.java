package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.dto.request.PermissionRequest;
import com.upinmcSE.coffeeshop.dto.response.PermissionResponse;
import com.upinmcSE.coffeeshop.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {
    public Permission toPermission(PermissionRequest request){
        return Permission.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public PermissionResponse toPermissionResponse(Permission permission){
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
