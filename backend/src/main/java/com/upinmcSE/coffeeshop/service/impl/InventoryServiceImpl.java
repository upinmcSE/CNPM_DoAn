package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.dto.response.InventoryResponse;
import com.upinmcSE.coffeeshop.dto.response.PageResponse;
import com.upinmcSE.coffeeshop.entity.Inventory;
import com.upinmcSE.coffeeshop.entity.Product;
import com.upinmcSE.coffeeshop.exception.ErrorCode;
import com.upinmcSE.coffeeshop.exception.ErrorException;
import com.upinmcSE.coffeeshop.mapper.InventoryMapper;
import com.upinmcSE.coffeeshop.repository.InventoryRepository;
import com.upinmcSE.coffeeshop.repository.ProductRepository;
import com.upinmcSE.coffeeshop.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public void upQuantity(String productId, int quantity) {

        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(
                () -> new ErrorException(ErrorCode.NOT_FOUND_INVENTORY));

        inventory.setQuantity(inventory.getQuantity() + quantity);

        inventoryRepository.save(inventory);
    }

    @Override
    public void downQuantity(String productId, int quantity) {
//        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(
//                () -> new ErrorException(ErrorCode.NOT_FOUND_INVENTORY));
//        if(inventory.getQuantity() < quantity)
//            throw new ErrorException(ErrorCode.OUT_OF_STOCK);
//        inventory.setQuantity(inventory.getQuantity() - quantity);

    }

    @Override
    public PageResponse<InventoryResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        var pageData = inventoryRepository.findAll(pageable);
        return PageResponse.<InventoryResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(inventoryMapper::toInventoryResponse).toList())
                .build();

    }
}
