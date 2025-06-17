package com.chatbot.sqlassistant.exception;

import com.chatbot.sqlassistant.config.ErrorConstants;
import com.chatbot.sqlassistant.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getErrorCode() == ErrorConstants.NOT_FOUND_CODE) {
            status = HttpStatus.NOT_FOUND;
        } else if (ErrorConstants.BAD_REQUEST_CODE.equals(ex.getErrorCode())) {
            status = HttpStatus.BAD_REQUEST;
        }
        
        log.error("Exception occurred. Operation failed. {}", ex.getMessage());

        return new ResponseEntity<>(
                ApiResponse.error(status, "Operation failed", ex.getMessage()),
                status
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        log.error("Exception occurred. Validation failed. {}", fieldErrors);

        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.BAD_REQUEST, "Validation failed", fieldErrors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleConstraintViolationException(ConstraintViolationException ex) {
    	log.error("Exception occurred. Validation error {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.BAD_REQUEST, "Validation error", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
    	log.error("An unexpected error occurred. {}", ex.getMessage());
        return new ResponseEntity<>(
                ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
