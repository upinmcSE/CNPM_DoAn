package com.upinmcSE.coffeeshop.exception;

import com.upinmcSE.coffeeshop.dto.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

     //sXử lý lỗi chưa xác định
//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<SuccessResponse> handlingRuntimeException(RuntimeException exception){
//        SuccessResponse successResponse = new SuccessResponse<>();
//
//        successResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
//        successResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
//        return ResponseEntity.badRequest().body(successResponse);
//    }

    // Xử lý lỗi đã xác định
    @ExceptionHandler(value = ErrorException.class)
    ResponseEntity<SuccessResponse> handlingErrorException(ErrorException exception){
        ErrorCode errorCode = exception.getErrorCode();

        SuccessResponse successResponse = new SuccessResponse<>();
        successResponse.setCode(errorCode.getCode());
        successResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(successResponse);
    }



    // Xử lý lỗi không có quyền truy cập

    // Xử lý lỗi validation request
}
