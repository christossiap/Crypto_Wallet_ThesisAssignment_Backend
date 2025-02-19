package com.unipi.christossiap.crypto_wallet_thesisassignment.settings;

import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Map<String, Object> httpResponse(String message, Exception ex, WebRequest request, HttpStatus status) {
        ErrorAttributes errorAttributes = new DefaultErrorAttributes();
        Map<String, Object> map =
                errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        map.put("error", message);
        map.put("status", status);
        map.put("path", request.getDescription(false));
        return map;
    }
    private Map<String, Object> httpResponseErrorList(List<String> errors, WebRequest request, HttpStatus status) {
        ErrorAttributes errorAttributes = new DefaultErrorAttributes();
        Map<String, Object> map =
                errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        map.put("error", errors);
        map.put("status", status);
        map.put("path", request.getDescription(false));
        return map;
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, Object> map= httpResponse("Bad Request", ex, request, status);

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleException(MethodArgumentNotValidException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> errors = new ArrayList<>();
        for (var violation: ex.getBindingResult().getAllErrors())
            errors.add(violation.getDefaultMessage());

        Map<String, Object> map= httpResponseErrorList(
                errors, request, status);

        return new ResponseEntity<>(map, status);
    }


    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Map<String, Object>> handleException(BindException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> errors = new ArrayList<>();
        for (var violation: ex.getAllErrors())
            errors.add(violation.getDefaultMessage());

        Map<String, Object> map= httpResponseErrorList(
                errors, request, status);

        return new ResponseEntity<>(map, status);
    }
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleException(ConstraintViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> errorMessage = new ArrayList<>();
        for (var violation: ex.getConstraintViolations()) {
            errorMessage.add(violation.getMessageTemplate());
        }

        Map<String, Object> map = httpResponse(String.join(", ", errorMessage), ex, request, status);

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleException(ResourceNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Map<String, Object> map = httpResponse(ex.getMessage(), ex, request, status);

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleException(ResourceAlreadyExistsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Map<String, Object> map = httpResponse(ex.getMessage(), ex, request, status);

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleException(AccessDeniedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        Map<String, Object> map = httpResponse(ex.getMessage(), ex, request, status);

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<Map<String, Object>> handleException(AuthException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, Object> map = httpResponse(ex.getMessage(), ex, request, status);

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = UserValidationExceptions.class)
    public ResponseEntity<Map<String, Object>> handleException(UserValidationExceptions ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, Object> map = httpResponse(ex.getMessage(), ex, request, status);

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = BalanceDepositionException.class)
    public ResponseEntity<Map<String, Object>> handleException(BalanceDepositionException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, Object> map = httpResponse(ex.getMessage(), ex, request, status);

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = CodeNotMatchingException.class)
    public ResponseEntity<Map<String, Object>> handleException(CodeNotMatchingException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, Object> map = httpResponse(ex.getMessage(), ex, request, status);

        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> map= httpResponse("Internal Server Error", ex,request, status);

        return new ResponseEntity<>(map, status);
    }
}
