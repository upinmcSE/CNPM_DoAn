package com.upinmcSE.coffeeshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    MOMO_EXCEPTION(7777, "MoMo error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(8888,"Unauthentication", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User name existed", HttpStatus.BAD_REQUEST),
    NOT_MATCH_PW(1003, "password not matched", HttpStatus.BAD_REQUEST),
    NOT_FOUND_ROLE(1004,"Not found role", HttpStatus.NOT_FOUND),
    EMPLOY_LV(1005,"employee lv not found", HttpStatus.NOT_FOUND),
    WORK_TIME(1006, "Not found work time", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(1007, "Role existed", HttpStatus.BAD_REQUEST),
    PERRMISSION_EXISTED(1008, "Permission existed", HttpStatus.BAD_REQUEST),
    NOT_FOUND_PERMISSION(1009,"Not found permission", HttpStatus.NOT_FOUND),
    NOT_FOUND_CATEGORY(2001, "category not found", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(2002, "Category existed", HttpStatus.BAD_REQUEST),
    NOT_FOUND_PRODUCT(2003, "Not found product", HttpStatus.NOT_FOUND),
    PRODUCT_EXISTED(2004, "Product existed", HttpStatus.BAD_REQUEST),
    FILE_LARGE(2005,"File size is too large", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE(2006, "Invalid file type, only image files are accepted", HttpStatus.BAD_REQUEST),
    FILE_EMPTY(2007, "File is empty", HttpStatus.NOT_FOUND),
    NOT_FOUND_CUSTOMER(2008, "Not found customer", HttpStatus.NOT_FOUND),
    NOT_FOUND_EMPLOYEE(2009, "Not found employee", HttpStatus.NOT_FOUND),
    NOT_FOUND_ORDER(3001, "Not found order", HttpStatus.NOT_FOUND)

    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
