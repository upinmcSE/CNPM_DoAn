package com.upinmcSE.coffeeshop.controller;

import com.upinmcSE.coffeeshop.dto.request.PermissionRequest;
import com.upinmcSE.coffeeshop.dto.response.PermissionResponse;
import com.upinmcSE.coffeeshop.service.impl.PermissionServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionServiceImpl permissionService;
    @PostMapping("/add")
    public ResponseEntity<PermissionResponse> add(@RequestBody PermissionRequest request){
        return ResponseEntity.ok().body(permissionService.add(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PermissionResponse>> getAll(){
        return ResponseEntity.ok().body(permissionService.getAll());
    }

}
