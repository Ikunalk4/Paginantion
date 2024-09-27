package com.sample.pagination.exception.responsehandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sample.pagination.exception.ProductsNotFoundExceptionSteps;
import com.sample.pagination.exception.handler.ErrorMessages;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(ProductsNotFoundExceptionSteps.class)
    public ResponseEntity<ErrorMessages> userServiceErrorHandler(ProductsNotFoundExceptionSteps ex) {

        ErrorMessages errorMessages = ErrorMessages.builder()
                .messages(ex.getMessage())
                .status("NOT FOUND")
                .build();

        return new ResponseEntity<>(errorMessages, HttpStatus.NOT_ACCEPTABLE);
    }
	
}
