package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.RoleRequest;
import com.upinmcSE.coffeeshop.dto.response.RoleResponse;
import com.upinmcSE.coffeeshop.service.impl.RoleServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleServiceImpl roleService;

    @PostMapping("/add")
    public ResponseEntity<RoleResponse> add(@RequestBody RoleRequest request){
        return ResponseEntity.ok().body(roleService.add(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<RoleResponse>> getAll(){
        return ResponseEntity.ok().body(roleService.getAll());
    }

}
