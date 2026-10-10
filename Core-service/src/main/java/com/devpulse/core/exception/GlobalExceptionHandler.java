package com.devpulse.core.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(
	        ResourceNotFoundException exception) {

	    Map<String, String> error = Map.of(
	            "error", exception.getMessage()
	    );

	    return ResponseEntity
	            .status(HttpStatus.NOT_FOUND)
	            .body(error);
	}
	

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationException(
        MethodArgumentNotValidException exception) {

    Map<String, String> error = new HashMap<>();

    String message = exception.getBindingResult()
            .getFieldErrors()
            .get(0)
            .getDefaultMessage();

    error.put("error", message);

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
}

}