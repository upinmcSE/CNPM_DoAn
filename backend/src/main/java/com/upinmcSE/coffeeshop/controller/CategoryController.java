package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.CategoryRequest;
import com.upinmcSE.coffeeshop.dto.response.CategoryResponse;
import com.upinmcSE.coffeeshop.service.impl.CategoryServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryResponse>> getAll(){
        return ResponseEntity.ok().body(categoryService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponse> update(@RequestParam("id") String id, @RequestBody CategoryRequest request){
        return ResponseEntity.ok().body(categoryService.update(id, request));
    }
}
