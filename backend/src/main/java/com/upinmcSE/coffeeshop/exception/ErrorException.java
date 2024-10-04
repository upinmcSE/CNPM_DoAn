package com.upinmcSE.coffeeshop.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorException extends RuntimeException{
    private ErrorCode errorCode;
    public ErrorException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
