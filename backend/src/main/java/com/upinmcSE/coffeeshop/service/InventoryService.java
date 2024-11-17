package com.upinmcSE.coffeeshop.service;

import com.upinmcSE.coffeeshop.dto.response.InventoryResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;

public interface InventoryService {
    void upQuantity(String productId, int quantity);
    void downQuantity(String productId, int quantity);
    PageResponse<InventoryResponse> getAll(int page, int size);
}
