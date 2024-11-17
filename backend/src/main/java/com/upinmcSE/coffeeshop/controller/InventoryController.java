package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.response.InventoryResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import com.upinmcSE.coffeeshop.service.impl.InventoryServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryController {

    InventoryServiceImpl inventoryService;

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<InventoryResponse>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok().body(inventoryService.getAll(page, size));
    }

    @PostMapping("/up-quantity")
    public ResponseEntity<String> upQuantity(@RequestParam String productId, @RequestParam int quantity) {
        System.out.println(productId + "|" + quantity);
        inventoryService.upQuantity(productId, quantity);
        return ResponseEntity.ok().body("Up quantity successfully");
    }
}
