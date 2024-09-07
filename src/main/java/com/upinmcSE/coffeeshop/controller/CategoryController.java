package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.CategoryRequest;
import com.upinmcSE.coffeeshop.service.impl.CategoryServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryServiceImpl categoryService;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody CategoryRequest request){
        return ResponseEntity.ok().body(categoryService.add(request));
    }
}
