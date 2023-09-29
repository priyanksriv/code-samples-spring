package com.prizy.common.controller;

import com.prizy.common.exception.ApiException;
import com.prizy.common.exception.BadRequestException;
import com.prizy.common.exception.ErrorMessage;
import com.prizy.common.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@RestControllerAdvice
public final class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorMessage> handleApiException(ApiException ex) {
        ErrorMessage err = new ErrorMessage(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorMessage> handleException(MethodArgumentNotValidException ex) {
        String errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));

        ErrorMessage err = new ErrorMessage(errors);
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorMessage> handleNotFound(RecordNotFoundException ex) {
        ErrorMessage err = new ErrorMessage(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorMessage> handleBadRequest(BadRequestException ex) {
        ErrorMessage err = new ErrorMessage(ex);
        return ResponseEntity.badRequest().body(err);
    }
}
