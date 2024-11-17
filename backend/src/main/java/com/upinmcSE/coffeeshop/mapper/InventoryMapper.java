package com.upinmcSE.coffeeshop.mapper;

import com.upinmcSE.coffeeshop.dto.response.InventoryResponse;
import com.upinmcSE.coffeeshop.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {
    public InventoryResponse toInventoryResponse(Inventory inventory){
        return InventoryResponse.builder()
                .id(inventory.getId())
                .quantity(inventory.getQuantity())
                .productId(inventory.getProduct().getId())
                .productName(inventory.getProduct().getName())
                .build();
    }
}
