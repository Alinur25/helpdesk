package org.example.helpdesk.Exception;

import org.example.helpdesk.Dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;



import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .timestamp(Instant.now())
                        .status(404)
                        .error("Not Found")
                        .message(ex.getMessage())
                        .path(req.getRequestURI())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .orElse("Validation error");

        return ResponseEntity.badRequest()
                .body(ApiError.builder()
                        .timestamp(Instant.now())
                        .status(400)
                        .error("Bad Request")
                        .message(msg)
                        .path(req.getRequestURI())
                        .build());
    }
}