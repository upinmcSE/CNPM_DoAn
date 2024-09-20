package com.upinmcSE.coffeeshop.dto.request;

import com.upinmcSE.coffeeshop.entity.Permission;

import java.util.Set;

public record RoleRequest(
        String name,
        String description,
        Set<String> permissions
) {
}
