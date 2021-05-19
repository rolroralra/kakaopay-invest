package com.kakaopay.invest.demo.controller;

import com.kakaopay.invest.demo.controller.dto.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ApiResult<?> handleException(Exception e) {
        return ApiResult.failed(e);
    }
}