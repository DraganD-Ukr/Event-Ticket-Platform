package com.dragand.event_ticket_platform_api.exception;

import com.dragand.event_ticket_platform_api.dto.ErrorDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFound(ResourceNotFoundException ex) {

        log.error("Caught ResourceNotFoundException: {}", ex.getMessage(), ex);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setError(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException ex) {

        log.error("Caught ConstraintViolationException: {}", ex.getMessage(), ex);

        ErrorDto errorDto = new ErrorDto();

        // Collect validation error messages
        String errorMsg = ex.getConstraintViolations()
                .stream()
                        .findFirst()
                                .map(violation ->
                                        violation.getPropertyPath() + ": " + violation.getMessage()
                                ).orElse("Constraint violation occurred");

        errorDto.setError(errorMsg);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        log.error("Caught MethodArgumentNotValidException: {}", ex.getMessage(), ex);
        ErrorDto errorDto = new ErrorDto();

        BindingResult bindingResult = ex.getBindingResult();
        String errorMsg = bindingResult.getFieldErrors()
                .stream()
                .findFirst()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Validation error");

        errorDto.setError(errorMsg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {

        log.error("Caught exception: {}", ex.getMessage(), ex);

        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

}
