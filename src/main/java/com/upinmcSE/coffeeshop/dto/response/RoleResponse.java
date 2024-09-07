package com.upinmcSE.coffeeshop.dto.response;

import com.upinmcSE.coffeeshop.entity.Permission;
import lombok.Builder;

import java.util.Set;
@Builder
public record RoleResponse(
        String id,
        String name,
        String description,
        Set<Permission> permissions
) {
}
